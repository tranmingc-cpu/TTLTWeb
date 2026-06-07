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
        // Lấy các phần tử HTML của Modal Xóa
        const confirmModal = document.getElementById('custom-confirm-modal');
        const cancelBtn = document.getElementById('btn-confirm-cancel');
        const confirmDeleteBtn = document.getElementById('btn-confirm-delete');
        const modalMessage = document.getElementById('modal-message');
        const successToast = document.getElementById('success-toast');

        let deleteUrl = "";

        // Lắng nghe sự kiện click trên toàn trang để tìm nút xóa
        document.addEventListener('click', function (e) {
            const triggerBtn = e.target.closest('.btn-delete-trigger');

            if (triggerBtn) {
                e.preventDefault(); // Chặn chuyển hướng trang ngay lập tức

                deleteUrl = triggerBtn.getAttribute('data-url'); // Lấy link xóa từ data-url
                const customMsg = triggerBtn.getAttribute('data-message'); // Lấy tin nhắn riêng nếu có

                modalMessage.innerText = customMsg ? customMsg : "Bạn có chắc chắn muốn xóa mục này không? Hành động này không thể hoàn tác.";
                confirmModal.classList.remove('hidden'); // Mở modal
            }
        });

        // Bấm Hủy -> Đóng modal
        cancelBtn.addEventListener('click', closeModal);

        // Bấm Ra Ngoài Vùng Trống -> Đóng modal
        confirmModal.addEventListener('click', function (e) {
            if (e.target === confirmModal) {
                closeModal();
            }
        });

        // Bấm Vẫn Xóa -> Thực hiện chuyển hướng đến Servlet
        confirmDeleteBtn.addEventListener('click', function () {
            if (deleteUrl !== "") {
                window.location.href = deleteUrl;
            }
        });

        // Hàm đóng modal và reset đường dẫn
        function closeModal() {
            confirmModal.classList.add('hidden');
            deleteUrl = "";
        }



        // Kiểm tra xem JSTL có nhận được thông báo thành công từ Session không
        const hasMessage = "${not empty sessionScope.successMessage}" === "true";

        if (hasMessage) {
            // Hiện Toast thành công ở góc phải màn hình
            successToast.classList.remove('hidden');

            // Tạo hiệu ứng tự động biến mất sau 3 giây
            setTimeout(() => {
                successToast.style.opacity = "0";
                successToast.style.transform = "translateY(-20px)";
                setTimeout(() => {
                    successToast.classList.add('hidden');
                    // Reset lại style để lần sau hiển thị bình thường
                    successToast.style.opacity = "1";
                    successToast.style.transform = "translateY(0)";
                }, 300);
            }, 3000);

            // Xóa ngay session message bằng mã Java để tránh việc người dùng F5 bị lặp lại thông báo
            <%
                if (session.getAttribute("successMessage") != null) {
                    session.removeAttribute("successMessage");
                }
            %>
        }
    });
</script>