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
    showGrid: false
});

function get_click_coordinates(xClick, yClick) {
    let calculatorRect = elt.getBoundingClientRect();
    return calculator.pixelsToMath({
        x: xClick - calculatorRect.left,
        y: yClick - calculatorRect.top
    })
}

function clearGraph() {
    calculator.getExpressions().forEach(expression => {
        calculator.removeExpression({id: expression.id});
    })
}

function draw_point(x, y, hit) {
    if (hit == null) return;
    calculator.setExpression({
        latex: `(${x}, ${y})`, // Задание координат точки
        color: hit ? 'green' : 'red' // Цвет точки
    });
}

function draw_graph(r) {
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