function enableEdit(commentId) {
    console.log("Enable edit clicked for comment ID: ", commentId);
    const commentText = document.getElementById(`comment-text-${commentId}`);
    const editForm = document.getElementById(`edit-form-${commentId}`);
    if (!commentText) {
        console.error(`Element with ID comment-text-${commentId} not found`);
        return;
    }
    if (!editForm) {
        console.error(`Element with ID edit-form-${commentId} not found`);
        return;
    }
    // 숨기기: 기존 댓글 텍스트
    commentText.style.display = 'none';
    // 표시하기: 댓글 수정 폼
    editForm.style.display = 'block';
}

function cancelEdit(commentId) {
    // 표시하기: 기존 댓글 텍스트
    document.getElementById(`comment-text-${commentId}`).style.display = 'block';

    // 숨기기: 댓글 수정 폼
    document.getElementById(`edit-form-${commentId}`).style.display = 'none';
}
