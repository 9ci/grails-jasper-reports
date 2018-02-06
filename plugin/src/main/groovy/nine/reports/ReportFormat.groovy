package nine.reports

import groovy.transform.CompileStatic

@CompileStatic
enum ReportFormat {
    PDF("application/pdf",true),
    HTML("text/html"),
    PNG("text/html"),
    //XML("text/xml"),
    //JSON("text/json"),
    CSV("text/csv",true),
    //XLS("application/vnd.ms-excel",true),
    TEXT("text/plain"),
    //DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document",true),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",true)

    String mimeType
    //whether to set up cache headers to download or just stream to browser
    boolean downloadContent = false


    private ReportFormat(String mimeType, boolean downloadContent = false) {
        this.mimeType = mimeType
        this.downloadContent = downloadContent
    }

    String getExtension(){
        name().toLowerCase()
    }

    /**
     * valueOf using toString.toUpperCase() of passed object
     */
    public static ReportFormat get(nm) {
        try {
            return valueOf(nm.toString().toUpperCase())
        }catch (IllegalArgumentException e) {
            return null
        }
    }

    public static List<String> toList() {
        ReportFormat.values()*.name()
    }

}