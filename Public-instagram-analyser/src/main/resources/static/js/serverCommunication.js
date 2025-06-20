
const sentFolders = new Set(); // Set to keep track of folders that have already been sent

// Function to send files from the selected folder to the server and request a Thymeleaf page
export async function sendFilesToServer(folderName, fileMap) {
    const resultArea = document.getElementById('result-area'); // HTML element to display the result of the operation
    console.log('Sending files for folder:', folderName);
    console.log('fileMap:', Array.from(fileMap.entries())); // Log the fileMap for debugging

    // Check if the folder has already been sent
    if (sentFolders.has(folderName)) {
        resultArea.innerHTML += `<p>Files for ${folderName} have already been sent! Proceeding to load stats page...</p>`;
        // Skip file sending, but proceed with Thymeleaf page request
        try {
            const pageResponse = await fetch(`http://localhost:8080/api/conversation/fileStats/${folderName}`, {
                method: 'GET'
            });

            // If the response is successful, redirect to the Thymeleaf page
            if (pageResponse.ok) {
                console.log(`Opening http://localhost:8080/api/conversation/fileStats/${folderName} in a new tab`);
                window.open(`http://localhost:8080/api/conversation/fileStats/${folderName}`, '_blank'); // Open in a new tab
            } else {
                console.log(`Failed to load page for ${folderName}. Response status: ${pageResponse.status}`);
                resultArea.innerHTML += `<p>Failed to load page for ${folderName}.</p>`;
            }
        } catch (error) {
            console.error('Error loading Thymeleaf page:', error);
            resultArea.innerHTML += `<p>Error loading page for ${folderName}</p>`;
        }
        return; // Exit function
    }

    // Retrieve files from the fileMap using the folder name
    const files = fileMap.get(folderName);
    console.log(`Files for ${folderName}:`, files); // Log files for debugging

    if (!files) {
        resultArea.innerHTML += `<p>No files found for folder ${folderName}.</p>`;
        return;
    }

    resultArea.innerHTML = `<p>Sending files for ${folderName}...</p>`;

    // Loop through the files and send them one by one to the server
    for (const file of files) {
        const formData = new FormData(); // Create a FormData object to hold the file and folder name
        formData.append('file', file); // Append the file to the form data
        formData.append('folderName', folderName); // Append the folder name to the form data

        // Fetch CSRF token and header name from meta tags
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        try {
            // Send the file to the server using a POST request
            const response = await fetch('http://localhost:8080/api/noStorage/upload', {
                method: 'POST',
                headers: {
                [csrfHeader]: csrfToken}, // add CSRF token to headers
                body: formData
            });

            // Check if the response is successful
            if (!response.ok) {
                throw new Error(`Failed to send file ${file.name} for folder ${folderName}`);
            }
        } catch (error) {
            console.error('Error sending file:', error);
            resultArea.innerHTML += `<p>Error sending file ${file.name}</p>`;
        }
    }

    resultArea.innerHTML += `<p>All files for ${folderName} have been sent successfully!</p>`;
    sentFolders.add(folderName); // Mark the folder as successfully sent

    // After sending the files, request the Thymeleaf page for the folder's statistics
    try {
        const pageResponse = await fetch(`http://localhost:8080/api/conversation/fileStats/${folderName}`, {
            method: 'GET'
        });
        if (pageResponse.ok) {
            console.log(`Opening http://localhost:8080/api/conversation/fileStats/${folderName} in a new tab`);
            window.open(`http://localhost:8080/api/conversation/fileStats/${folderName}`, '_blank'); // Open in a new tab
        } else {
            console.log(`Failed to load page for ${folderName}. Response status: ${pageResponse.status}`);
            resultArea.innerHTML += `<p>Failed to load page for ${folderName}.</p>`;
        }
//        if (pageResponse.ok) {
//            console.log(`Redirecting to http://localhost:8080/api/conversation/fileStats/${folderName}`);
//            window.location.href = `http://localhost:8080/api/conversation/fileStats/${folderName}`; // Redirect to the Thymeleaf page
//        } else {
//            resultArea.innerHTML += `<p>Failed to load page for ${folderName}.</p>`;
//        }
    } catch (error) {
        console.error('Error loading Thymeleaf page:', error);
        resultArea.innerHTML += `<p>Error loading page for ${folderName}</p>`;
    }
}

// Function to test the server connection by sending test data
export async function testConnection() {
    const resultArea = document.getElementById('result-area'); // HTML element to display the test connection result
    resultArea.innerHTML = '<p>Sending test data to the server...</p>'; // Inform the user that the test data is being sent

    try {
        // Send test data to the server using a POST request
        const response = await fetch('http://localhost:8080/api/test/connection', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken }, // Set the content type to JSON
            body: JSON.stringify({ test: 'Connection Test' }) // Send test data in the body of the request
        });

        // If the server responds successfully, display the response text
        if (response.ok) {
            resultArea.innerHTML = `<p>Server responded: ${await response.text()}</p>`;
        } else {
            // If there is an error, display a message indicating a server error
            resultArea.innerHTML = '<p>Server responded with an error.</p>';
        }
    } catch (error) {
        // Log and display an error if the request to the server fails
        console.error('Error communicating with server:', error);
        resultArea.innerHTML = '<p>Failed to communicate with the server.</p>';
    }
}

