package th.ac.kbu.cs.ExamProject.Description;

public class RoleDescription {
	public class PropertyWithQuote {
		public static final String TEACHER = "'ROLE_TEACHER'";
		public static final String ADMIN = "'ROLE_ADMIN'";
		public static final String STUDENT = "'ROLE_STUDENT'";	
	}
	public class Property {
		public static final String TEACHER = "ROLE_TEACHER";
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String STUDENT = "ROLE_STUDENT";	
	}
	public class hasRole {
		public static final String TEACHER = "hasRole('ROLE_TEACHER')";
		public static final String ADMIN = "hasRole('ROLE_ADMIN')";
		public static final String STUDENT = "hasRole('ROLE_STUDENT')";
	}
	public class hasAnyRole{
		public class ADMIN {
			public static final String WITHTEACHER = "hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')";
			public static final String WITHSTUDENT = "hasAnyRole('ROLE_ADMIN','ROLE_STUDENT')";	
		}
		public class TEACHER {
			public static final String WITHADMIN = "hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')";
			public static final String WITHSTUDENT = "hasAnyRole('ROLE_TEACHER','ROLE_STUDENT')";
		}
		public class STUDENT {
			public static final String WITHADMIN = "hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')";
			public static final String WITHTEACHER = "hasAnyRole('ROLE_STUDENT','ROLE_TEACHER')";
		}
		public static final String WITHBOTH = "hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')";
	}
}
