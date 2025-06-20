// Import necessary functions from different modules for handling files, updating UI, and communicating with the server
import { unzipFiles } from './fileHandler.js';
import { displayParticipantButtons } from './uiHandler.js';
import { testConnection, sendFilesToServer } from './serverCommunication.js';

// Initialize a Map to store files by folder name and a Set to track already sent folders
const fileMap = new Map();
const sentFolders = new Set();

// Add event listener to handle file selection from the input element with ID 'zipFileInput'
document.getElementById('zipFileInput').addEventListener('change', async function(event) {
    const file = event.target.files[0]; // Get the selected file
    if (!file) return; // Exit if no file is selected

    try {
        // Unzip the selected file and store its contents in the fileMap
        await unzipFiles(file, fileMap);
        // Update UI by displaying participant buttons based on the unzipped data
        displayParticipantButtons(fileMap);
    } catch (error) {
        // Log and display an error if unzipping fails
        console.error("Error unzipping files:", error);
        document.getElementById('result-area').innerHTML = `<p>Error unzipping files: ${error.message}</p>`;
    }
});

// Function to print the contents of fileMap (folder name and associated files) to the UI
function printFileMap() {
    const outputDiv = document.getElementById('fileMapOutput');
    outputDiv.innerHTML = ''; // Clear previous output

    if (fileMap.size === 0) {
        outputDiv.innerHTML = '<p>fileMap is empty.</p>';
        return;
    }

    // Iterate over each folder in the fileMap and display its name and files
    fileMap.forEach((files, folderName) => {
        outputDiv.innerHTML += `<p>Folder: ${folderName}, Files: ${files.map(file => file.name).join(', ')}</p>`;
    });
}

// Add event listener to button to print the fileMap when clicked
document.getElementById('printFileMapButton').addEventListener('click', printFileMap);

// Function to collect JSON files from a specified folder and store them in fileMap
async function collectJsonFiles(folder, folderPath) {
    console.log(`Collecting files from folder: ${folderPath}`); // Log the current folder being processed
    const promises = []; // Initialize array for promises to handle file processing

    // Iterate through files and subfolders in the current folder
    folder.forEach(function(relativePath, file) {
        if (file.dir) {
            // If the item is a subfolder, recursively collect files from it
            console.log(`Found subfolder: ${relativePath}`);
            promises.push(collectJsonFiles(zip.folder(relativePath), folderPath + relativePath));
        } else if (relativePath.endsWith('.json')) {
            // If the item is a JSON file, process it
            console.log(`Found JSON file: ${relativePath}`);
            promises.push(file.async('blob').then(fileContent => {
                // Extract folder name from the path and store the JSON file in fileMap
                const folderName = folderPath.split('/').slice(-2, -1)[0]; // Get the folder name
                if (!fileMap.has(folderName)) {
                    fileMap.set(folderName, []); // Create entry for folder if it doesn't exist
                }
                // Add the JSON file to the fileMap under its corresponding folder
                fileMap.get(folderName).push(new File([fileContent], relativePath));
            }));
        }
    });

    // Wait for all file processing tasks to complete
    await Promise.all(promises);
}
// Add an event listener to the HTML element with the ID 'sendFilesButton'
// This will trigger when the user clicks the "Send Files" button (click event)
//document.getElementById('sendFilesButton').addEventListener('click', function() {
//    const folderName = prompt("Enter the folder name to send:"); // Prompt the user for a folder name to send
//    if (folderName) {
//        sendFilesToServer(folderName); // Call the sendFilesToServer function to send the specified folder to the server
//    }
//});

// You can call testConnection on a button click or page load
//document.getElementById('testConnectionButton').addEventListener('click', testConnection);

//document.getElementById('uploadForm').addEventListener('submit', function(event) {
//    event.preventDefault();
//
//    const fileInput = document.getElementById('fileInput');
//    const file = fileInput.files[0];
//    if (!file) {
//        alert('Please select a file.');
//        return;
//    }
//
//    const formData = new FormData();
//    formData.append('file', file);
//
//    fetch('http://localhost:8080/api/upload-json', {
//        method: 'POST',
//        body: formData
//    })
//    .then(response => response.json())
//    .then(data => {
//        console.log('Success:', data);
//    })
//    .catch((error) => {
//        console.error('Error:', error);
//    });
//});
//
//document.getElementById('button').addEventListener('click', fetchData;
//// Function to fetch data from Spring Boot server
//    async function fetchData() {
//        try {
//            const response = await fetch('/api/jsonNoStorage/getNames');
//            if (!response.ok) throw new Error('Network response was not ok');
//            const data = await response.json();
//
//            // Visualize the data
//            const dataContainer = document.getElementById('data-container');
//            dataContainer.innerHTML = `
//                <p>Value 1: ${data.value1}</p>
//                <p>Value 2: ${data.value2}</p>
//            `;
//        } catch (error) {
//            console.error('There was a problem with the fetch operation:', error);
//        }
//    }
// Function to test server connection
//async function testConnection() {
//    const resultArea = document.getElementById('result-area');
//    resultArea.innerHTML = '<p>Sending test data to the server...</p>';
//
//    try {
//        const response = await fetch('http://localhost:8080/api/test/connection', {
//            method: 'POST',
//            headers: { 'Content-Type': 'application/json' },
//            body: JSON.stringify({ test: 'Connection Test' })
//        });
//
//        if (response.ok) {
//            resultArea.innerHTML = `<p>Server responded: ${await response.text()}</p>`;
//        } else {
//            resultArea.innerHTML = '<p>Server responded with an error.</p>';
//        }
//    } catch (error) {
//        console.error('Error communicating with server:', error);
//        resultArea.innerHTML = '<p>Failed to communicate with the server.</p>';
//    }
//}