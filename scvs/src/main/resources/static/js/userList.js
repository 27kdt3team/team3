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
  }
});
