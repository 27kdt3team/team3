// 'selectAll' 체크박스를 선택
var masterCheckbox = document.getElementById('selectAll');

// 모든 체크박스를 선택
var checkboxes = document.querySelectorAll('input[name="userIds"]');

// 삭제 버튼 선택
var deleteButton = document.getElementById('deleteSubmit');

// 마스터 체크박스 클릭 시 모든 체크박스의 상태를 설정하는 함수
function toggleCheckboxes(master) {
  checkboxes.forEach(function(checkbox) {
    checkbox.checked = master.checked;
  });
}

// 각 체크박스에 변경 이벤트를 추가하여 마스터 체크박스 상태를 업데이트
checkboxes.forEach(function(checkbox) {
  checkbox.addEventListener('change', function() {
    // 모든 체크박스가 선택되었는지 확인
    var allChecked = Array.from(checkboxes).every(function(checkbox) {
      return checkbox.checked;
    });

    // 마스터 체크박스의 상태 업데이트
    masterCheckbox.checked = allChecked;
  });
});

// 마스터 체크박스에 클릭 이벤트 리스너 추가
masterCheckbox.addEventListener('click', function() {
  toggleCheckboxes(masterCheckbox);
});

// 삭제 버튼 클릭 시 체크박스 선택 여부 확인
deleteButton.addEventListener('click', function(event) {

  // 체크된 체크박스가 하나라도 있는지 확인
  var isAnyChecked = Array.from(checkboxes).some(function(checkbox) {
    return checkbox.checked;
  });

  if (!isAnyChecked) {
    event.preventDefault(); // 폼 제출 막기
  } else {
    const confirmed = confirm("이 사용자의 계정을 삭제 처리하겠습니까?");

        if (confirmed) {
            alert("계정이 삭제되었습니다.");
        } else {
           event.preventDefault(); // 폼 제출 막기
        }

  }


});

function sortTable(columnIndex) {
    // 첫 번째 열(체크박스 열)은 클릭해도 아무 작업을 하지 않음
    if (columnIndex === 0) {
        return;
    }

    const table = document.getElementById("userTable");
    const rows = Array.from(table.rows).slice(1); // 헤더 제외한 행 가져오기
    let ascending = table.dataset.sortOrder !== "asc";

    // 첫 번째 열 제외: 화살표 초기화
    const headers = Array.from(table.querySelectorAll("th .sort-icon")).slice(1); // 첫 번째 열 제외
    headers.forEach((icon, index) => {
        icon.textContent = index + 1 === columnIndex ? (ascending ? "▲" : "▼") : "▲"; // 클릭한 열만 업데이트
    });

    // 정렬 로직
    rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[columnIndex]?.innerText.trim();
        const cellB = rowB.cells[columnIndex]?.innerText.trim();

        if (!isNaN(cellA) && !isNaN(cellB)) {
            // 숫자 비교
            return ascending ? Number(cellA) - Number(cellB) : Number(cellB) - Number(cellA);
        } else {
            // 문자열 비교
            return ascending
                ? cellA.localeCompare(cellB)
                : cellB.localeCompare(cellA);
        }
    });

    // 정렬된 행 다시 테이블에 추가
    rows.forEach(row => table.tBodies[0].appendChild(row));

    // 정렬 상태 저장
    table.dataset.sortOrder = ascending ? "asc" : "desc";
}







