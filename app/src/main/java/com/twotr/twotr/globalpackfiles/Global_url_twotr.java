package com.twotr.twotr.globalpackfiles;


public class Global_url_twotr {

    public static  String Base_url ="http://twotr.com:5040/api/";
    public static  String Base_Imgurl ="http://twotr.com:5040/api/files";

    public static  String User_signup= Base_url + "users";
    public  static  String User_signin=Base_url +"auth/login";


    public static String Profile_grades=Base_url +"userinfo/grades";
    public static String Profile_timezone=Base_url +"userinfo/timezones";
    public static String Profile_subject=Base_url +"subject/search?key=";
    public static String Profile_education_level=Base_url +"userinfo/education/search?type=major&key=";
    public static String Profile_institute_level=Base_url +"userinfo/education/search?type=institution&key=";
    public static String Profile_document_type=Base_url +"userinfo/documents";
    public static String User_Profile_education=Base_url +"userinfo/education/update";
    public static String User_Profile_professional=Base_url +"userinfo/professional/update";
    public static String User_Profile_Id_verification=Base_url +"userinfo/id/update";

    public static String Profile_countrycode=Base_url +"userinfo/country/codes";

    public static String Profile_names=Base_url +"users/profile";


    public static String Profile_create=Base_url +"userinfo/basic/create";

    public static String Profile_update=Base_url +"userinfo/basic/update";

    public static String Profile_getdetails=Base_url +"userinfo/basic";

    public static String Image_Base_url="http://twotr.com:5040/files";


    public static String Profile_subject_grade_spin=Base_url +"userinfo/basic/profile";


    public static String Profile_subject_create=Base_url +"subject/create";

    public static String Profile_subject_create_class=Base_url +"class/create";

    public  static String Profile_image_profile=Base_Imgurl+"/profile/";
    public  static String Profile_image_id_verification=Base_Imgurl+"/userinfo/verification/id/";
    public  static String Profile_dashboard=Base_url +"class/dashboard/upcoming?page=1&size=10";

public static  String Tutor_Schedule_update=Base_url+"class/upcoming/update/";
public static  String Tutor_schedule_delete=Base_url+"class/history/delete/";


public static String Tutor_number_send=Base_url+"verify/sms/send";

    public static String Tutor_number_resend=Base_url+"verify/sms/resend";

    public static String Tutor_number_send_otp_verify=Base_url+"verify/sms/confirm/";

    public static String Tutor_email_send=Base_url+"verify/email/send";


    public static String Tutor_email_resend=Base_url+"verify/email/resend";

    public static String Tutor_email_send_otp_verify=Base_url+"verify/email/confirm/";

    public static String Guest_list_api=Base_url+"search/class?name=";





}
