<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="custom-confirm-modal" class="modal-overlay hidden">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Xác nhận hành động</h3>
        </div>
        <div class="modal-body">
            <p id="modal-message">Bạn có chắc chắn muốn xóa mục này không? Hành động này không thể hoàn tác.</p>
        </div>
        <div class="modal-footer">
            <button id="btn-confirm-cancel" class="btn-secondary">Hủy</button>
            <button id="btn-confirm-delete" class="btn-primary">Vẫn xóa</button>
        </div>
    </div>
</div>

<div id="success-toast" class="toast-success hidden">
    <span class="toast-icon">✓</span>
    <span id="toast-message">${sessionScope.successMessage}</span>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const confirmModal = document.getElementById('custom-confirm-modal');
        const cancelBtn = document.getElementById('btn-confirm-cancel');
        const confirmDeleteBtn = document.getElementById('btn-confirm-delete');
        const modalMessage = document.getElementById('modal-message');
        const successToast = document.getElementById('success-toast');

        let deleteUrl = "";

        document.addEventListener('click', function (e) {
            const triggerBtn = e.target.closest('.btn-delete-trigger');

            if (triggerBtn) {
                e.preventDefault();

                deleteUrl = triggerBtn.getAttribute('data-url');
                const customMsg = triggerBtn.getAttribute('data-message');

                modalMessage.innerText = customMsg ? customMsg : "Bạn có chắc chắn muốn xóa mục này không? Hành động này không thể hoàn tác.";
                confirmModal.classList.remove('hidden');
            }
        });

        cancelBtn.addEventListener('click', closeModal);

        confirmModal.addEventListener('click', function (e) {
            if (e.target === confirmModal) {
                closeModal();
            }
        });

        confirmDeleteBtn.addEventListener('click', function () {
            if (deleteUrl !== "") {
                window.location.href = deleteUrl;
            }
        });

        function closeModal() {
            confirmModal.classList.add('hidden');
            deleteUrl = "";
        }



        const hasMessage = "${not empty sessionScope.successMessage}" === "true";

        if (hasMessage) {
            successToast.classList.remove('hidden');

            setTimeout(() => {
                successToast.style.opacity = "0";
                successToast.style.transform = "translateY(-20px)";
                setTimeout(() => {
                    successToast.classList.add('hidden');
                    successToast.style.opacity = "1";
                    successToast.style.transform = "translateY(0)";
                }, 300);
            }, 3000);

            <%
                if (session.getAttribute("successMessage") != null) {
                    session.removeAttribute("successMessage");
                }
            %>
        }
    });
</script>