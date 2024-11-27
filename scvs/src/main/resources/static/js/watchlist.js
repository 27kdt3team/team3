document.addEventListener("DOMContentLoaded", function () {
    // Watchlist 필터링
    const watchlistSearchInput = document.getElementById("watchlistSearchInput");
    const watchlistTable = document.getElementById("watchlistTable");
    const cancelSearchBtn = document.getElementById("cancelSearchBtn"); // "Cancel" 버튼 참조

    const watchlistSearchForm = document.getElementById("watchlistSearchForm");
        watchlistSearchForm.addEventListener("submit", function (event) {
            event.preventDefault(); // 폼 제출을 방지하여 Enter 키를 눌러도 페이지가 새로고침되지 않도록 함
        });
    const allTickerSearchForm = document.getElementById("allTickerSearchForm");
       allTickerSearchForm.addEventListener("submit",function(event){
            event.preventDefault();
       })

    watchlistSearchInput.addEventListener("input", function () {
        filterTable(watchlistTable, watchlistSearchInput.value);
    });

    cancelwatchlistSearchBtn.addEventListener("click", function () {
        watchlistSearchInput.value = ""; // 입력 필드 비우기
        filterTable(watchlistTable, ""); // 모든 행을 다시 보이도록 필터링 초기화
    });

    cancelALLTickersSearchBtn.addEventListener("click", function(){
        allTickersSearchInput.value = "";
        filterTable(allTickersTable,"");
    });


    // 전체 티커 필터링
    const allTickersSearchInput = document.getElementById("allTickersSearchInput");
    const allTickersTable = document.getElementById("allTickersTable");
    allTickersSearchInput.addEventListener("input", function () {
        filterTable(allTickersTable, allTickersSearchInput.value);
    });

    function filterTable(table, filterText) {
        const rows = table.getElementsByTagName("tr");
        for (let i = 1; i < rows.length; i++) { // 첫 번째 행은 테이블 헤더이므로 제외
            const row = rows[i];
            const companyCell = row.getElementsByTagName("td")[0]; // 첫 번째 셀은 회사 이름
            const codeCell = row.getElementsByTagName("td")[1]; // 두 번째 셀은 코드

            if (companyCell && codeCell) {
                const companyName = companyCell.textContent.toLowerCase();
                const code = codeCell.textContent.toLowerCase();
                const filter = filterText.toLowerCase();

                // 회사 이름이나 코드 중 하나라도 검색어를 포함하면 해당 행을 보이도록 설정
                if (companyName.includes(filter) || code.includes(filter)) {
                    row.style.display = ""; // 일치하면 보이도록 설정
                } else {
                    row.style.display = "none"; // 일치하지 않으면 숨김
                }
            }
        }
    }

    // 페이지네이션 관련 설정은 기존 코드와 동일하게 유지
    const rowsPerPage = 7;

    // 관심 목록 페이지네이션
    const watchlistRows = watchlistTable.querySelectorAll("tbody tr");
    const watchlistLoadMoreBtn = document.getElementById("loadMoreBtn");
    let watchlistCurrentIndex = 0;

    // 전체 티커 목록 페이지네이션
    const allTickersRows = allTickersTable.querySelectorAll("tbody tr");
    const allTickersLoadMoreBtn = document.getElementById("allTickersLoadMoreBtn");
    let allTickersCurrentIndex = 0;

    // 공통 함수: 행을 보이도록 설정
    function showRows(rows, currentIndex, rowsPerPage, loadMoreBtn) {
        for (let i = currentIndex; i < currentIndex + rowsPerPage && i < rows.length; i++) {
            rows[i].style.display = ""; // 보여줄 행의 display 속성을 빈 문자열로 설정
        }
        currentIndex += rowsPerPage;

        // 만약 더 이상 보여줄 행이 없다면 "더보기" 버튼 숨기기
        if (currentIndex >= rows.length) {
            loadMoreBtn.style.display = "none";
        } else {
            loadMoreBtn.style.display = "block";
        }

        return currentIndex; // 현재 인덱스 업데이트하여 반환
    }

    // 관심 종목 목록 초기화
    watchlistRows.forEach(row => row.style.display = "none"); // 모든 행을 처음에는 숨기기
    watchlistCurrentIndex = showRows(watchlistRows, watchlistCurrentIndex, rowsPerPage, watchlistLoadMoreBtn);

    // 관심 종목 목록 "더보기" 버튼 클릭 이벤트 리스너
    watchlistLoadMoreBtn.addEventListener("click", function () {
        watchlistCurrentIndex = showRows(watchlistRows, watchlistCurrentIndex, rowsPerPage, watchlistLoadMoreBtn);
    });

    // 전체 티커 목록 초기화
    allTickersRows.forEach(row => row.style.display = "none"); // 모든 행을 처음에는 숨기기
    allTickersCurrentIndex = showRows(allTickersRows, allTickersCurrentIndex, rowsPerPage, allTickersLoadMoreBtn);

    // 전체 티커 목록 "더보기" 버튼 클릭 이벤트 리스너
    allTickersLoadMoreBtn.addEventListener("click", function () {
        allTickersCurrentIndex = showRows(allTickersRows, allTickersCurrentIndex, rowsPerPage, allTickersLoadMoreBtn);
    });
});
