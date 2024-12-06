(async () => {
    const proxyUrl = "https://thingproxy.freeboard.io/fetch/";

    // Thymeleaf로 전달된 `stockInfo.symbol`과 `stockInfo.company`를 동적으로 가져오기
    const stockSymbolElement = document.querySelector('.symbol'); // Symbol을 포함한 요소
    const marketElement = document.querySelector('.current-price span:last-child'); // market 텍스트 포함 요소

    let stockSymbol = "";

    if (stockSymbolElement && marketElement) {
        // Symbol과 Market 값을 가져오기
        stockSymbol = stockSymbolElement.textContent.trim();
        const market = marketElement.textContent.trim();

        // Market 값에 따라 Symbol 수정
        if (market === '₩') {
            stockSymbol += '.KS'; // KOR 시장이면 .KS 추가
        }
    }

    const companyName = document.querySelector('.company-name').textContent;

    const apiUrl = `https://query2.finance.yahoo.com/v7/finance/chart/${stockSymbol}?interval=1wk&range=max`;
    try {
        // Yahoo Finance 데이터 가져오기
        const response = await fetch(proxyUrl + apiUrl);
        console.log(response)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        // 데이터 검증
        if (!data || !data.chart || !data.chart.result) {
            throw new Error("데이터 형식이 올바르지 않습니다.");
        }

        const timestamps = data.chart.result[0].timestamp;
        const indicators = data.chart.result[0].indicators.quote[0];
        const open = indicators.open;
        const high = indicators.high;
        const low = indicators.low;
        const close = indicators.close;
        const volume = indicators.volume;

        const ohlc = [];
        const volumeSeries = [];

        for (let i = 0; i < timestamps.length; i++) {
            const time = timestamps[i] * 1000; // Unix timestamp to milliseconds
            ohlc.push([time, open[i], high[i], low[i], close[i]]);
            volumeSeries.push([time, volume[i]]);
        }

        // 데이터 그룹화 단위
        const groupingUnits = [
            ["week", [1]], // 주 단위
            ["month", [1, 2, 3, 4, 6]] // 월 단위
        ];

        // 차트 생성
        Highcharts.stockChart("stock-graph", {
            rangeSelector: {
                selected: 4
            },

            title: {
                text: `${companyName} 주식 차트`
            },

            yAxis: [
                {
                    labels: {
                        align: "right",
                        x: -3
                    },
                    title: {
                        text: "OHLC"
                    },
                    height: "60%",
                    lineWidth: 2,
                    resize: {
                        enabled: true
                    }
                },
                {
                    labels: {
                        align: "right",
                        x: -3
                    },
                    title: {
                        text: "Volume"
                    },
                    top: "65%",
                    height: "35%",
                    offset: 0,
                    lineWidth: 2
                }
            ],

            tooltip: {
                split: true
            },

            series: [
                {
                    type: "candlestick",
                    name: companyName, // 회사명 동적으로 설정
                    data: ohlc,
                    dataGrouping: {
                        units: groupingUnits
                    }
                },
                {
                    type: "column",
                    name: "거래량",
                    data: volumeSeries,
                    yAxis: 1,
                    dataGrouping: {
                        units: groupingUnits
                    }
                }
            ]
        });
    } catch (error) {
        console.error("데이터 가져오기 실패:", error);
        alert("데이터를 가져오는 중 오류가 발생했습니다.");
    }
})();
