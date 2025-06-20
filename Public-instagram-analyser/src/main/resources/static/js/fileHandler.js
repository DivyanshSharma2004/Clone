// Function to unzip a file, collect JSON files from "inbox" folders, and store them in fileMap
export async function unzipFiles(file, fileMap) {
    // Load the ZIP file using JSZip and wait for it to be read as an archive
    const zip = await JSZip.loadAsync(file);
    
    // Array to store folders that contain "inbox/" in their path
    const inboxFolders = [];

    // Helper function to recursively collect JSON files from folders into the fileMap
    async function collectJsonFiles(folder, folderPath) {
        console.log(`Collecting files from folder: ${folderPath}`); // Log the folder being processed
        const promises = []; // Array to store promises for file processing tasks

        // Iterate through the files and subfolders within the provided folder
        folder.forEach(function(relativePath, file) {
            if (file.dir) {
                // If the item is a directory, log it and recursively collect files from it
                console.log(`Found subfolder: ${relativePath}`);
                promises.push(collectJsonFiles(zip.folder(relativePath), folderPath + relativePath));
            } else if (relativePath.endsWith('.json')) {
                // If the item is a JSON file, process it
                console.log(`Found JSON file: ${relativePath}`);
                promises.push(file.async('blob').then(fileContent => {
                    // Extract the folder name from the path to use as a key in fileMap
                    const folderName = folderPath.split('/').slice(-2, -1)[0];
                    
                    // If the folderName isn't in fileMap, initialize an entry for it
                    if (!fileMap.has(folderName)) {
                        fileMap.set(folderName, []);
                    }
                    
                    // Add the processed file (as a File object) to the fileMap under the folder name
                    fileMap.get(folderName).push(new File([fileContent], relativePath));
                }));
            }
        });

        // Wait for all file processing promises to complete before returning
        await Promise.all(promises);
    }

    // Traverse through the ZIP contents and find folders containing "inbox/" in their path
    zip.forEach(function(relativePath, file) {
        if (file.dir && relativePath.includes('inbox/')) {
            // If the item is a directory and contains "inbox" in the path, add it to inboxFolders
            inboxFolders.push({ folder: zip.folder(relativePath), path: relativePath });
        }
    });

    // Process all inbox folders and collect their JSON files
    await Promise.all(inboxFolders.map(inboxFolder => collectJsonFiles(inboxFolder.folder, inboxFolder.path)));
}
