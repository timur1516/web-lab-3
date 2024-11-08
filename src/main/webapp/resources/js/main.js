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
    calculator.setExpression({
        id: 'area1',
        latex: `x^{2}+y^{2}<=${r}^2\\{y>=0\\}\\{x>=0\\}`,
        color: Desmos.Colors.BLUE
    });
    calculator.setExpression({
        id: 'area2',
        latex: `y<=2x+${r}\\{y>=0\\}\\{x<=0\\}`,
        color: Desmos.Colors.BLUE
    });
    calculator.setExpression({
        id: 'area3',
        latex: `-${r}<=x<=0\\{-${r}/2<=y<=0\\}`,
        color: Desmos.Colors.BLUE
    });
    calculator.setExpression({
        id: 'line1',
        latex: `y=0\\{-${r}<=x<=-${r}/2\\}`,
        color: Desmos.Colors.BLUE
    });
    calculator.setExpression({
        id: 'line2',
        latex: `y=-${r}/2\\{-${r}<=x<=0\\}`,
        color: Desmos.Colors.BLUE
    });
    calculator.setExpression({
        id: 'line3',
        latex: `y=0\\{0<=x<=${r}\\}`,
        color: Desmos.Colors.BLUE
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
