package Upload;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/uploadImage")
@MultipartConfig
public class UploadfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // gọi ảnh
        Part part = request.getPart("image");
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        // Lưu vào src/main/webapp/images
        String uploadPath = getServletContext().getRealPath("/images");
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        part.write(uploadPath + File.separator + fileName);

        response.getWriter().println("Upload OK");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
}