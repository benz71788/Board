package net.board.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFileDownAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String fileName = request.getParameter("filename");
		System.out.println("fileName = " + fileName);
		
		String savePath = "boardupload";
		
		//서블릿의 실행 환경 정보를 담고 있는 객체를 리턴합니다.
		//(application 내장 객체를 리턴합니다.)
		ServletContext context = request.getServletContext();
		String sDownloadPath = context.getRealPath(savePath);
		//위 두 문장을 한 문장으로 나타내면 다음과 같습니다.
//		String sDownloadPath = application.getRealPath(savePath);
		String sFilePath = sDownloadPath + "/" + fileName;
		System.out.println(sFilePath);
		
		byte b[] = new byte[4096];
		
		String sMimeType = context.getMimeType(sFilePath);
		System.out.println("sMimeType>>" + sMimeType);
		
		if(sMimeType == null) {
			sMimeType = "application/octet-stream";
		}
		
		response.setContentType(sMimeType);
		
		//- 이 부분이 한글 파일명이 깨지는 것을 방지해 줍니다.
		String sEncoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		System.out.println(sEncoding);
		
		//Content-Disposition: attachment: 브러우저는 해당 content
		response.setHeader("Content-Disposition", "attachment; filename=" + sEncoding);
		
		ServletOutputStream so = response.getOutputStream();
		BufferedOutputStream out2 = null;
		BufferedInputStream in = null;
		try {
			//웹브라우저로의 출력 스트림 생성합니다.
			out2 = new BufferedOutputStream(so);
			//sFilePath로 지정한 파일에 대한 입력 스트림을 생성합니다.
			in = new BufferedInputStream(new FileInputStream(sFilePath));
			
			int numRead;
			//read(b, 0, b.length) : 바이트 배열 b의 0부터 b.leognth
			//크기 만큼 읽어옵니다.
			while((numRead = in.read(b, 0, b.length)) != -1) {
				//바이트 배열 b의 0번부터 numRead크기 만큼 브라우저로 출력
				out2.write(b, 0, numRead);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(out2 != null){
					out2.flush();
					out2.close();
				}
				if(in != null){
					in.close();
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
