//attributes hashset
const attributes = [
"emojis", "contextual", "pharmacologyTerms", "optometryTerms", "physicsTerms", "bakingTerms", "aiTerms", "obstetricsAndGynecologyTerms", "fantasyTerms", "agricultureTerms", "comedyTerms", "geneticsTerms", "specialEducationTerms", "dataScienceTerms", "museumStudiesTerms", "immunologyTerms", "wearableTechnologyTerms", "games", "machineLearningTerms", "lawTerms", "parentingTerms", "realEstateTerms", "selfImprovementTerms", "newsTerms", "nursingTerms", "fishingTerms", "microbiologyTerms", "cookingTerms", "adventureTerms", "spaceExplorationTerms", "cybersecurityTerms", "socialMedia", "orthopedicsTerms", "campingTerms", "toxicologyTerms", "productivityTerms", "hikingTerms", "pharmacyTerms", "historyTerms", "cloudComputingTerms", "householdTerms", "paleontologyTerms", "scienceFictionTerms", "fashionTerms", "arTerms", "gossipTerms", "architectureTerms", "businessTerms", "teacherTrainingTerms", "colors", "documentaryTerms", "mathTerms", "psychologyTerms", "anatomyTerms", "economyTerms", "spaceTerms", "horrorTerms", "geologyTerms", "dancingTerms", "vrTerms", "adultEducationTerms", "stockMarketTerms", "forestryTerms", "educationTerms", "philosophyTerms", "chemistryTerms", "ecommerceTerms", "literacyTerms", "clothingTerms", "mythologyTerms", "podcastTerms", "photographyTerms", "people", "educationalAssessmentTerms", "wildlifeTerms", "weatherConditions", "renewableEnergyTerms", "places", "theaterTerms", "oceanographyTerms", "pathologyTerms", "travelTerms", "entertainmentTerms", "mindfulnessTerms", "archaeologyTerms", "gardeningTerms", "fisheriesTerms", "attributes", "basicQuestions", "gamesTerms", "relationshipTerms", "feelings", "artTerms", "environmentTerms", "oncologyTerms", "libraryScienceTerms", "theologyTerms", "musicTerms", "sleepTerms", "curriculumDevelopmentTerms", "bodyParts", "skiingTerms", "religionTerms", "videographyTerms", "psychiatryTerms", "beautyTerms", "natureTerms", "animalTerms", "friendTerms", "astronomyTerms", "nutritionTerms", "technologyTerms", "socialMediaTerms", "locationTerms", "marketingTerms", "neurologyTerms", "languageLearningTerms", "transportationTerms", "biomedicalEngineering", "surgeryTerms", "academicResearchTerms", "workTerms", "mobileTechnologyTerms", "shortResponses", "sportTerms", "gerontologyTerms", "bankingTerms", "publicHealthTerms", "climateChangeTerms", "innovationTerms", "petTerms", "blockchainTerms", "sociologyTerms", "psychotherapyTerms", "cultureTerms", "environmentalScienceTerms", "dramaTerms", "educationalPsychologyTerms", "ethicsTerms", "schoolSafetyTerms", "veterinaryMedicineTerms", "educationalTechnologyTerms", "socialWorkTerms", "earlyChildhoodEducationTerms", "politicsTerms", "educationPolicyTerms", "romanceTerms", "biotechnologyTerms", "languageTerms", "financeTerms", "carrerTerms", "distanceLearningTerms", "holidayTerms", "cryptocurrencyTerms", "scienceTerms", "anthropologyTerms", "higherEducationTerms", "emergencyMedicineTerms", "vehicleTerms", "biologyTerms", "boatingTerms", "plantTerms", "crimeTerms", "craftsTerms", "schoolAdministrationTerms", "shoppingTerms", "insuranceTerms", "healthTerms", "foodTerms", "iotTerms", "mysteryTerms", "fitnessTerms", "neuroscienceTerms", "roboticsTerms", "meteorologyTerms", "cardiologyTerms", "pediatricsTerms", "literatureTerms", "molecularBiologyTerms", "hobbyTerms", "exercisesTerms", "sustainabilityTerms", "time", "surfingTerms", "dentistryTerms", "physiologyTerms"
];

let toggleStates = {};

function searchFunction() {
    const input = document.getElementById('searchBox').value.toLowerCase();
    const suggestionsBox = document.getElementById('suggestions');

    // Clear previous suggestions
    suggestionsBox.innerHTML = '';

    // If input is empty, hide suggestions box
    if (!input) {
        suggestionsBox.style.display = 'none';
        return;
    }

    // Filter attributes based on the input value
    const filteredAttributes = attributes.filter(attr => attr.toLowerCase().includes(input));

    // Display suggestions
    if (filteredAttributes.length > 0) {
        suggestionsBox.style.display = 'block';
        filteredAttributes.forEach(attribute => {
            const suggestionItem = document.createElement('div');
            suggestionItem.classList.add('suggestion-item');
            suggestionItem.textContent = attribute;

            const toggleContainer = document.createElement('div');
            toggleContainer.classList.add('toggle-container', 'neutral');
            toggleContainer.setAttribute('data-attribute', attribute);
            toggleContainer.onclick = () => toggleMode(toggleContainer);

            const toggleCircle = document.createElement('div');
            toggleCircle.classList.add('toggle-circle');
            toggleContainer.appendChild(toggleCircle);

            suggestionItem.appendChild(toggleContainer);
            suggestionsBox.appendChild(suggestionItem);

            // Remember the state of each toggle for each attribute
            if (toggleStates[attribute]) {
                toggleContainer.classList.remove('neutral', 'green', 'red');
                toggleContainer.classList.add(toggleStates[attribute]);
            }
        });
    } else {
        suggestionsBox.style.display = 'none';
    }

    updateActiveStates();
}

function toggleMode(toggle) {
    const attribute = toggle.getAttribute('data-attribute');
    let currentClass = toggle.classList.contains('green') ? 'green' : toggle.classList.contains('red') ? 'red' : 'neutral';
    let nextClass;

    if (currentClass === 'neutral') {
        nextClass = 'green';
    } else if (currentClass === 'green') {
        nextClass = 'red';
    } else {
        nextClass = 'neutral';
    }

    toggle.classList.remove('neutral', 'green', 'red');
    toggle.classList.add(nextClass);

    // Save the state for persistence
    toggleStates[attribute] = nextClass;

    updateActiveStates();
}
function updateActiveStates() {
    const activeStatesContainer = document.getElementById('activeStates');
    activeStatesContainer.innerHTML = ''; // Clear existing active states

    // Show only non-neutral toggle states
    for (let attribute in toggleStates) {
        if (toggleStates[attribute] !== 'neutral') {
            const activeStateDiv = document.createElement('div');
            activeStateDiv.classList.add('active-state');
            activeStateDiv.textContent = `${attribute}: ${toggleStates[attribute]}`;
            activeStateDiv.style.backgroundColor = toggleStates[attribute];

            const closeButton = document.createElement('button');
            closeButton.textContent = 'X';
            closeButton.classList.add('close-btn');
            closeButton.onclick = () => removeActiveState(attribute, activeStateDiv);

            activeStateDiv.appendChild(closeButton);
            activeStatesContainer.appendChild(activeStateDiv);
        }
    }
}

function removeActiveState(attribute, activeStateDiv) {
    // Reset the toggle state to neutral
    toggleStates[attribute] = 'neutral';

    // Find the toggle button and reset it
    const toggleContainer = document.querySelector(`[data-attribute="${attribute}"]`);
    if (toggleContainer) {
        toggleContainer.classList.remove('green', 'red');
        toggleContainer.classList.add('neutral');
    }

    // Remove the active state from the UI
    activeStateDiv.remove();
}

function submitAttributes() {
// Get desired (green) and undesired (red) attributes
const desiredAttributes = Object.keys(toggleStates).filter(attr => toggleStates[attr] === 'green');
const undesiredAttributes = Object.keys(toggleStates).filter(attr => toggleStates[attr] === 'red');

// Fetch CSRF token and header name from meta tags
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

// Data to send in the POST request
    const requestData = {
        folderName: folderName,
        desiredAttributes: desiredAttributes,
        undesiredAttributes: undesiredAttributes
    };

// Log the request data to the console
console.log('Request Data:', requestData);

// Send POST request using Fetch API
fetch('http://localhost:8080/api/conversation/getMessagesByAttributes', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken  // CSRF token in headers
    },
    body: JSON.stringify(requestData)
})
.then(response => response.json())  // Parse JSON response
.then(data => {
    console.log(data);
    displayMessages(data);  // Display messages in a table
    plotGraph(data);        // Plot graph with data
})
.catch(error => {
    console.error('Error:', error);
});
}

let chartInstance = null;
const ctx = document.getElementById("messageChart").getContext("2d");

function plotGraph(messages) {
    if (!messages.length) return;

    // 1. Sort messages by timestamp_ms and filter invalid values
    const sortedMessages = [...messages]
        .sort((a, b) => parseInt(a.timestamp_ms) - parseInt(b.timestamp_ms))
        .filter(msg => {
            const timestamp = parseInt(msg.timestamp_ms);
            return !isNaN(timestamp) && timestamp > 0; // Validate timestamp_ms
        });

    if (sortedMessages.length === 0) {
        console.error("No valid timestamps found.");
        return;
    }

    // 2. Convert timestamp_ms to Date objects
    const timestamps = sortedMessages.map(msg =>
        new Date(parseInt(msg.timestamp_ms)) // Parse milliseconds
    );

    const firstDate = timestamps[0];
    const lastDate = timestamps[timestamps.length - 1];
    const totalDays = (lastDate - firstDate) / (1000 * 60 * 60 * 24);
    const useWeeks = totalDays < 60;

    const groupedData = new Map();

    // 3. Group by week/month (with NaN guards)
    timestamps.forEach(date => {
        if (isNaN(date.getTime())) return; // Skip invalid dates

        let key;
        if (useWeeks) {
            const { year, week } = getISOWeek(date);
            key = `${year}-W${week.toString().padStart(2, '0')}`;
        } else {
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            key = `${year}-${month.toString().padStart(2, '0')}`;
        }

        groupedData.set(key, (groupedData.get(key) || 0) + 1);
    });

    const labels = Array.from(groupedData.keys());
    const dataPoints = Array.from(groupedData.values());

    if (chartInstance) chartInstance.destroy();

    // 4. Create the chart
    chartInstance = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: `Messages Per ${useWeeks ? "Week" : "Month"}`,
                data: dataPoints,
                borderColor: "blue",
                borderWidth: 2,
                fill: false,
                pointRadius: 5
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: useWeeks ? "Weeks (ISO)" : "Months" } },
                y: { title: { display: true, text: "Message Count" }, beginAtZero: true }
            }
        }
    });
}

// Keep the same getISOWeek function as before
function getISOWeek(date) {
    const d = new Date(date);
    if (isNaN(d.getTime())) return { year: NaN, week: NaN };

    d.setHours(0, 0, 0, 0);
    d.setDate(d.getDate() + 4 - (d.getDay() || 7)); // Adjust to Thursday
    const yearStart = new Date(d.getFullYear(), 0, 1);
    const weekNumber = Math.ceil((((d - yearStart) / 86400000) + 1) / 7);

    let year = d.getFullYear();
    if (d.getMonth() === 0 && weekNumber > 4) {
        year--;
    } else if (d.getMonth() === 11 && weekNumber === 1) {
        year++;
    }

    return { year, week: weekNumber };
}

//fixes the encoding on special characters from latin to utf-8
function fixEncoding(str) {
    return new TextDecoder("utf-8").decode(Uint8Array.from(str.split("").map(c => c.charCodeAt(0))));
}

function displayMessages(messages) {
    // Create a table to display messages
    const tableContainer = document.getElementById('messagesTableContainer');
    tableContainer.innerHTML = ''; // Clear previous table

    if (messages && messages.length > 0) {
        // Create table headers
        const table = document.createElement('table');
        table.border = '1';
        const headers = ['Sender Name', 'Content', 'Timestamp'];
        const thead = document.createElement('thead');
        const tr = document.createElement('tr');
        headers.forEach(header => {
            const th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        });
        thead.appendChild(tr);
        table.appendChild(thead);

        // Create table body and populate with messages
        const tbody = document.createElement('tbody');
        messages.forEach(message => {
            const tr = document.createElement('tr');

            const tdSender = document.createElement('td');
            tdSender.textContent = message.sender_name;

            const tdContent = document.createElement('td');
            tdContent.textContent = fixEncoding(message.content);

            const tdTimestamp = document.createElement('td');
            tdTimestamp.textContent = formatTimestampIST(message.timestamp_ms);

            tr.appendChild(tdSender);
            tr.appendChild(tdContent);
            tr.appendChild(tdTimestamp);
            tbody.appendChild(tr);
        });

        table.appendChild(tbody);
        tableContainer.appendChild(table);
    } else {
        tableContainer.textContent = 'No messages found.';
    }
}

// Function to format timestamp in Irish Standard Time (IST)
function formatTimestampIST(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString("en-IE", { timeZone: "Europe/Dublin" });
}

//Make sure the dom has loaded in properly
// Add event listener to detect Enter key press in the search box
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('searchBox').addEventListener('keydown', function(event) {
        if (event.key === 'Enter') { // Detect if Enter key is pressed
            clearSearch(); // Call the clearSearch function
        }
    });
});

function clearSearch() {
    // Clear the search box
    document.getElementById('searchBox').value = '';

    // Hide the suggestions box and clear its content
    const suggestionsBox = document.getElementById('suggestions');
    suggestionsBox.style.display = 'none';
    suggestionsBox.innerHTML = '';
}
//endpoint does not work yet 23/02/2025 written
function getSurroundingMessages() {
    const folderName = document.getElementById("folderName").value;
    const index = document.getElementById("index").value;
    const timeStamp = document.getElementById("timeStamp").value;

    fetch(`http://localhost:8080/api/conversation/getSurroundingMessages?folderName=${folderName}&index=${index}&timeStamp=${timeStamp}`)
        .then(response => response.json())
        .then(data => {
            const messagesList = document.getElementById("messagesList");
            messagesList.innerHTML = "";
            data.forEach(message => {
                const li = document.createElement("li");
                li.textContent = message.text; // Assuming messages have a "text" property
                messagesList.appendChild(li);
            });
        })
        .catch(error => console.error("Error fetching messages:", error));
}

document.getElementById("messageForm").addEventListener("submit", function(event) {
    event.preventDefault();
    getSurroundingMessages();
});

