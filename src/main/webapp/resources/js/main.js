const elt = document.getElementById('calculator');
const calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,
    lockViewport: true,
    zoomFit: true,
    pointsOfInterest: false,
    trace: false,
    xAxisStep: 1,
    yAxisStep: 1,
    showGrid: false,
    // backgroundColor: '#020920'
});

function get_click_coordinates(xClick, yClick) {
    let calculatorRect = elt.getBoundingClientRect();
    return calculator.pixelsToMath({
        x: xClick - calculatorRect.left,
        y: yClick - calculatorRect.top
    })
}

function draw_point(x, y, hit) {
    if(hit == null) return;
    calculator.setExpression({
        latex: `(${x}, ${y})`, // Задание координат точки
        color: hit ? 'green' : 'red' // Цвет точки
    });
}

function draw_batman(r) {
    let k = 49 / r
    calculator.setExpression({
        id: 'k',
        latex: 'k=' + k.toString()
    });
    calculator.setExpression({
        id: 's',
        latex: '\\s(x)=\\sqrt{(\\abs(x*k/7))/(x*k/7)}',
        hidden: true
    });
    calculator.setExpression({
        id: 'graph2',
        latex: '(x*k/49)^2*s(\\abs(x*k/7)-3)+(y*k/21)^2*s(y*k/7+3*\\sqrt{33}/7)-1=0',
        color: 'purple'
    });
    calculator.setExpression({
        id: 'graph3',
        latex: '\\abs(x*k/14)-((3*\\sqrt{33}-7)/112)*(x*k/7)^2-3+\\sqrt{1-(\\abs(\\abs(x*k/7)-2)-1)^2}-y*k/7=0',
        color: 'purple'
    });
    calculator.setExpression({
        id: 'graph4',
        latex: '9*s((1-\\abs(x*k/7))*(\\abs(x*k/7)-.75))-8*\\abs(x*k/7)-y*k/7=0',
        color: 'purple'
    });
    calculator.setExpression({
        id: 'graph5',
        latex: '3*\\abs(x*k/7)+.75*s((.75-\\abs(x*k/7))*(\\abs(x*k/7)-.5))-y*k/7=0',
        color: 'purple'
    });
    calculator.setExpression({
        id: 'graph6',
        latex: '2.25*s((.5-x*k/7)*(x*k/7+.5))-y*k/7=0',
        color: 'purple'
    });
    calculator.setExpression({
        id: 'graph7',
        latex: '6*\\sqrt{10}/7+(1.5-.5*\\abs(x*k/7))*s(\\abs(x*k/7)-1)-6*\\sqrt{10}/14*\\sqrt{4-(\\abs(x*k/7)-1)^2}-y*k/7=0',
        color: 'purple'
    });

    calculator.setMathBounds({
        left: -r - 1,
        right: r + 1,
        bottom: -r - 1,
        top: r + 1
    });
}

window.addEventListener("load", load_data);

document.getElementById('calculator').addEventListener('click', async function (evt) {
    let {x, y} = get_click_coordinates(evt.clientX, evt.clientY);
    try {
        await checkPoint([{name: 'x', value: x}, {name: 'y', value: y}]);
    } catch (e){
        alert('Сервер недоступен. Пожалуйста, попробуйте позже');
    }
});

function load_data() {
    draw_batman(Number(document.getElementById("form:r_slider").value));
    const table = document.querySelector("#archive tbody");
    const rows = table.querySelectorAll("tr");
    rows.forEach(row => {
        let cells = row.querySelectorAll("td");
        if(cells[0].textContent === '') return;
        let x = Number(cells[0].textContent.replace(',', '.'));
        let y = Number(cells[1].textContent.replace(',', '.'));
        let hit = cells[3].textContent === 'Попал';
        draw_point(x, y, hit);
    })
}
