
const BASE_PATH = "http://localhost:3030"

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

fetch(BASE_PATH + "/api/room", {mode: 'cors'}).then(res => res.json())
    .then((data) => {
        const ctxMovement = document.getElementById('roomDataChartMovement');
        const ctxLight = document.getElementById('roomDataChartLight');

        plotDataHistory(data.light, ctxLight, "Light History")
        plotDataHistory(data.movement, ctxMovement, "Movement History")
    })

const plotDataHistory = (data, ctx, label) => {

    const xValues = data.map(x => new Date(x.key).toLocaleString())
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