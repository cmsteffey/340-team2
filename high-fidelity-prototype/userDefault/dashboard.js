
function filterCoaches(){
    const input = document.getElementById("coachSearchBar").value.toLowerCase();
    const results = document.getElementsByClassName("searchResult");

    for(let i = 0; i < results.length; i++){
        const spans = results[i].getElementsByTagName("span");
        let match = false;

        for (let j = 0; j < spans.length; j++){
            const text = spans[j].textContent.toLowerCase();
            if(text.includes(input)) {
                match = true;
                break;
            }
        }

        results[i].style.display = match ? "" : "none";
    }
}