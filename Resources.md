UrlResource:URL对应的资源，根据一个URL地址即可构建  
ClassPathResource：获取类路径下的资源文件  
FileSystemResource：获取文件系统里面的资源  
ServletContextResource：ServletContext封装的资源，用于访问ServletContent环境下的资源。  
InputStreamResource：针对输入流封装的资源  
ByteArrayResource：针对一字节数组封装的资源  

Context实现了ResourceLoader接口：
getResource(String location):获取Resource。
