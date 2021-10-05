/*
* Ex2 web programing by: Eran Reuven 205410848
* Client side SPA design using pure JS.
* */

/*-------Closure Section--------*/
let SurveyApp = ( ()=> {
    const headers = {   // headers to send for the Ajax for JSON content.
        'Accept': 'application/json',
        'Content-type': 'application/json',
    };
    let returnedObject = {}; //returned object from this closure
    let jsonRes; //the value of JSON that have returned from the fetch() request
    let theSurvey = null; //first fetch request will hold here the survey itself. so we load it only once.

    //Msgs to interact the user

    let alrdyvote = `<strong>Hey!</strong> you already voted. You can only vote once!`;
    let notValid = `<strong>We are sorry!</strong>
                    <br> The survey is not available right now. Please try again later!`;
    let voted = `<strong>Thank you!</strong>
                    <br> Your vote as been submitted. A cookie registered in your browser.`;

    //End of msgs to interact the user

    /*-------newFetch Function--------
    * This function is to read the first time the survey from the server.
    * it called only once when the document is up. to promise that we only load the
    * survey once, we made an if to check if the survey member is empty or not.
    * if we dont get a json response means we have a problem. Explained further at javadoc
    * of the server - side.*/

      function newFetch(url, options){
        fetch(url, options).then(res => res.json()
        ).then((resp) => {
            openBtn();
            jsonRes = resp;
            if (theSurvey === null) {
                theSurvey = jsonRes;
                SurveyResults();
            }
        }).catch(() => AlertPresent(notValid, "alertBtn"))

    }
    /*-------AlertPresent Function--------
    * This function is generic to show an alert msg on our survey web app.
    * The function gets "val", the msg itself (one of the msgs above or even custom one)
    * and elementX (the element we want to input the msg. since we have two alerts - for
    * success and for fails.*/

    function AlertPresent(Val, elementX){
          let abtn = document.getElementById(elementX);
          abtn.innerHTML = Val;
          abtn.classList.add('d-block');
      }
    /*-------SurveyVoter Function--------
    * This function is called when "vote" button has been clicked.
    * it will make a formData to send to the servlet the vote value,
    * and it returns a json response with the vector of the votes.
    * it will catch an exception if none JSON value has been responded.*/

    async function SurveyVoter(value) {
        let formData = new FormData();
        formData.append("hasVote", value);
        fetch('ServletVote', {
            method: "POST",
            body: formData
        }).then(res => res.json()
        ).then((resp) => {
            AlertPresent(voted, "votedbtn");
            jsonRes = resp;
            printSurvey();
        }).catch(()=> AlertPresent(alrdyvote, "alertBtn"));
    }
    /*-------SurveyResults Function--------
    * This function calling newFetch function with details for ServletResults*/

    function SurveyResults(){
        newFetch('ServletResults', {
            method: "GET",
            headers: headers,
        });
    }
    /*-------SurveyLoader Function--------
    * This function calling newFetch function with details for ServletLoader
    * (can be called outside the closure by "SurveyApp.SurveyLoader() )*/

    returnedObject.SurveyLoader = () => {
         newFetch('ServletSurvey', {
            method: "GET",
            headers: headers,
        });
    }

    /*-------printSurvey Function--------
    * This function printing all the survey and vector of results. it calls once we done to
    * read all the details that needed from the server - side.*/

    function printSurvey(){
        document.getElementById("x-content").innerHTML = "";
        document.getElementById("x-header").innerHTML = theSurvey["0"];
        for(let i in theSurvey) {
            if (i !== "0") {
                document.getElementById("x-content").innerHTML += `<input class="form-check-input" type="radio" name=survey1 id= ${i} value=
                 ${i}><label class="form-check-label" for= ${i}> ${theSurvey[i]} &nbsp&nbsp So far votes: ${jsonRes[i]}</label><br/>`;
            }
        }
    }
    /*-------Event Listeners (buttons)---------*/
    document.getElementById('btn-open').addEventListener('click', (e)=>{
        closeBtn();
        printSurvey();

    })
    document.getElementById('btn-close').addEventListener('click', (e)=> {
        openBtn();
        document.getElementById("x-header").innerHTML = "";
        document.getElementById("x-content").innerHTML = "";
    })
    document.getElementById('btn-vote').addEventListener('click', (e)=> {
        let val = document.querySelector('input[name="survey1"]:checked').value;
        SurveyVoter(val).then(() => printSurvey());
    });
    /*-------End of Event Listeners (buttons)---------*/


    return returnedObject; //return the func object.
})();
/*--------Functions to load and unload buttons by call-----------*/
function openBtn() {
    document.getElementById('btn-open').classList.add("d-block");
    document.getElementById('btn-close').classList.remove("d-block");
    document.getElementById('btn-vote').classList.remove("d-block");
}
function closeBtn(){
    document.getElementById('btn-open').classList.remove("d-block");
    document.getElementById('btn-close').classList.add("d-block");
    document.getElementById('btn-vote').classList.add("d-block");
}
/*--------End of Functions to load and unload buttons by call-----------*/

/*To load all the details before the document even presents*/
window.onload = () => {
    SurveyApp.SurveyLoader();
}

/*To make it possible to hide a msg by clicking on it.*/
document.getElementById('alertBtn').addEventListener('click', () => {
    document.getElementById('alertBtn').classList.remove('d-block');

})
document.getElementById('votedbtn').addEventListener('click', () => {
    document.getElementById('votedbtn').classList.remove('d-block');

})