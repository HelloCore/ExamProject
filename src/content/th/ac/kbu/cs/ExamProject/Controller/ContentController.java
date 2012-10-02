package th.ac.kbu.cs.ExamProject.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import th.ac.kbu.cs.ExamProject.Description.RoleDescription;
import th.ac.kbu.cs.ExamProject.Domain.ContentDomain;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Controller
public class ContentController {
	
	@RequestMapping(value="/main/content.html")
	@PreAuthorize(RoleDescription.hasAnyRole.WITHBOTH)
	public ModelAndView init (@RequestParam(value="path",defaultValue="1",required=false) Long pathId
							,@RequestParam(value="error",required=false) String message
							,@RequestParam(value="success",required=false) Boolean success
							,@ModelAttribute ContentDomain domain
							,ModelAndView mv,HttpServletRequest request){
		
		domain.setCourseIdList(request);
		if(BeanUtils.isNull(pathId)){
			pathId = 1L;
		}
		if(pathId != 1L){
			domain.validatePermission(pathId);
		}
		mv.addObject("folderData", domain.getFolderData(BeanUtils.toLong(pathId)));
		mv.addObject("fileData",domain.getFileData(BeanUtils.toLong(pathId)));
		mv.addObject("currentPath", pathId);
		mv.addObject("parentPath",domain.getParentPath(BeanUtils.toLong(pathId)));
		Boolean canEdit = false;
		if(request.isUserInRole(RoleDescription.Property.ADMIN)){
			if(pathId.equals(1L)){
				canEdit = true;
			}
		}else if(request.isUserInRole(RoleDescription.Property.TEACHER)){
			if(!pathId.equals(1L)){
				canEdit = domain.checkCanEdit(pathId);
			}
		}
		mv.addObject("canEdit",canEdit);
		mv.addObject("error", message);
		mv.addObject("success",success);
		return mv;
	}
	
	@PreAuthorize(RoleDescription.hasAnyRole.ADMIN.WITHTEACHER)
	@RequestMapping(value="/main/content.html",method=RequestMethod.POST)
	public ModelAndView doMethod(@RequestParam(value = "method", required = true) String method,
			@ModelAttribute ContentDomain domain,HttpServletRequest request,HttpServletResponse response){
		System.out.println(method);
		if(method.equals("newFolder")){
			domain.newFolder(request);
		}else if (method.equals("uploadFile")){
			domain.uploadFile(request);
		}else if (method.equals("deleteFolder")){
			domain.deleteFolder(request);
		}else if (method.equals("deleteFile")){
			System.out.println("oh");
			domain.deleteFile(request);
		}
		ModelAndView mv = new ModelAndView("redirect:/main/content.html?path="+domain.getFolderId()+"&success=1");
		return mv;
	}
	
	
}
