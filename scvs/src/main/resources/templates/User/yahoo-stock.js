(async () => {
    const proxyUrl = "https://cors-anywhere.herokuapp.com/";
    const stockSymbol = "005930.KQ";
    const apiUrl = `https://query2.finance.yahoo.com/v7/finance/chart/${stockSymbol}?interval=1wk&range=1y`;

    // Yahoo Finance 데이터를 가져오는 함수
    const data = await fetch(proxyUrl + apiUrl)
        .then(response => response.json())
        .then(data => {
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

            return { ohlc, volume: volumeSeries };
        })
        .catch(error => {
            console.error("데이터 가져오기 실패:", error);
        });

    if (!data) {
        alert("데이터를 가져올 수 없습니다.");
        return;
    }

    // 데이터 그룹화 단위
    const groupingUnits = [
        ["week", [1]], // 주 단위
        ["month", [1, 2, 3, 4, 6]] // 월 단위
    ];

    // 차트 생성
    Highcharts.stockChart("container", {
        rangeSelector: {
            selected: 4
        },

        title: {
            text: "삼성전자 주식 차트"
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
                name: "삼성전자",
                data: data.ohlc,
                dataGrouping: {
                    units: groupingUnits
                }
            },
            {
                type: "column",
                name: "거래량",
                data: data.volume,
                yAxis: 1,
                dataGrouping: {
                    units: groupingUnits
                }
            }
        ]
    });
})();
