import { sendFilesToServer } from './serverCommunication.js'; // Import sendFilesToServer function from serverCommunication module

// Function to display buttons for each conversation folder from the fileMap
export function displayParticipantButtons(fileMap) {
    const namesContainer = document.getElementById('namesContainer'); // Get the HTML element where buttons will be added
    namesContainer.innerHTML = ''; // Clear the container to remove any previous buttons

    // Loop through the fileMap, which contains folder names as keys and files as values
    for (let [folderName, files] of fileMap) {
        const button = document.createElement('button'); // Create a new button element
        button.textContent = folderName; // Set the button's text to the folder name
        button.onclick = async () => {
            console.log(`Button clicked for folder: ${folderName}`); // Log folder name on click
            await sendFilesToServer(folderName, fileMap); // Call sendFilesToServer with folder name and fileMap
        };
        namesContainer.appendChild(button); // Add the button to the namesContainer element
        console.log(`Button created for folder: ${folderName}`); // Log a message indicating a button was created for the folder
    }
}

