
const BASE_PATH = "http://localhost:3030"

/**
 * Attach a submit event listener to the dashboard form:
 * on submit it sends a post request to the http service passing the new command.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("controllerForm").addEventListener("submit", e => {
        e.preventDefault()

        const formData = new FormData()
        formData.append("light", document.getElementById("chkLight").checked ? true : false)
        formData.append("movement", document.getElementById("sliderAngle").value)

        fetch(BASE_PATH + "/api/room", {
            mode: "cors", 
            method: "post",
            body: JSON.stringify(Object.fromEntries(formData))
        })

    })
})

/**
 * Get room data from the http service and plot them into charts
 */
fetch(BASE_PATH + "/api/room", {mode: 'cors'}).then(res => res.json())
    .then((data) => {
        const ctxMovement = document.getElementById('roomDataChartMovement');
        const ctxLight = document.getElementById('roomDataChartLight');

        plotDataHistory(data.light, ctxLight, "Light History")
        plotDataHistory(data.movement, ctxMovement, "Movement History")
    })

/**
 * Function to plot data using chart.js
 */
const plotDataHistory = (data, ctx, label) => {

    const xValues = data.map(x => new Date(x.date).toLocaleString())
    const yValues = data.map(y => y.value)

    new Chart(ctx, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: "rgba(0,0,0,1.0)",
                borderColor: "rgba(0,0,0,0.1)",
                data: yValues
            }]
        },    
        options: {
            plugins: {
                title: {
                    display: true,
                    text: label
                },
                legend: {
                    display: false
                 },
            },
        }
    });
}