package com.hualife.wxhb.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.hualife.mesiframework.core.exception.BusinessException;


public class OSSUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);
	/**
	 * 上传文件（输入流）到阿里云OSS
     * @param Constant.ossEndPoint 阿里云OSS提供的Constant.ossEndPoint
	 * @param Constant.ossId 阿里云OSS提供的Constant.ossId
	 * @param Constant.ossKey 阿里云OSS提供的Constant.ossKey
	 * @param Constant.ossBucket 阿里云OSS提供的Constant.ossBucket
	 * @param input　文件输入流
	 * @param ossPath　oss上保存文件的路径
	 * @throws Exception
	 */
	public static boolean uploadFileByInputStream(InputStream input, String ossPath) {
		OSSClient client=null;
		try {
			logger.info("文件上传开始...");
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentType("multipart/form-data");
			 client = createOSSClient();
			logger.info("上传文件:【{}】", ossPath);
			PutObjectResult result = client.putObject(Constant.ossBucket, ossPath, input, objectMeta);
			logger.info("上传阿里云OSS结果【{}】", result.getETag());
			return true;
		} catch (Exception e) {
			logger.error("上传阿里云OSS出错!!", e);
			throw new BusinessException("上传阿里云OSS出错!!");
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("上传阿里云OSS出错,关闭输入流出错!!", e);
					throw new BusinessException("上传阿里云OSS出错,关闭输入流出错!!");
				}
			}
			if(client!=null){
				try {
					client.shutdown();
				} catch (Exception e) {
					logger.error("上传阿里云OSS出错,关闭client连接出错!!", e);
					throw new BusinessException("上传阿里云OSS出错,关闭client连接出错!!");
				}
			}
			logger.info("文件上传结束...");
		}
	}

	/**
	 * 从阿里云OSS上下载文件 
	 * @param Constant.ossEndPoint 阿里云OSS提供的Constant.ossEndPointOut
	 * @param Constant.ossId 阿里云OSS提供的Constant.ossId
	 * @param Constant.ossKey 阿里云OSS提供的Constant.ossKey
	 * @param Constant.ossBucket 阿里云OSS提供的Constant.ossBucket
	 * @param path　阿里云OSS上保存文件的路径
	 * @return
	 */
	public static byte[] downloadFile(String path) {
		logger.info("文件下载开始...");
		logger.info("Constant.ossEndPoint【{}】,Constant.ossId【{}】,Constant.ossKey【{}】,Constant.ossBucket【{}】,path【{}】", Constant.ossEndPoint, Constant.ossId, Constant.ossKey, Constant.ossBucket, path);
		InputStream inputStream = null;
		ByteArrayOutputStream bos = null;
		OSSClient client=null;
		try {	
			client = createOSSClient();
			OSSObject ossObject = client.getObject(Constant.ossBucket, path);
			ObjectMetadata meta = client.getObjectMetadata(Constant.ossBucket, path);
			logger.info("下载文件【{}】", path);
			meta.getContentEncoding();			
			byte[] b = new byte[1024];
			int len;		
			inputStream = ossObject.getObjectContent();
			bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(b)) != -1) {
				bos.write(b, 0, len);
			}
		} catch (Throwable e) {
			logger.error("从阿里云OSS下载文件出错!【{}】", e.getMessage());
			throw new BusinessException("从阿里云OSS下载文件出错!");
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("从阿里云OSS下载文件出错,关闭输入流异常!【{}】", e);
					throw new BusinessException("从阿里云OSS下载文件出错,关闭输入流异常!");
				}
			}
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("从阿里云OSS下载文件出错,关闭输出流异常！【{}】", e);
					throw new BusinessException("从阿里云OSS下载文件出错,关闭输出流异常！");
				}
			}
			if(null!=client){
				try {
					client.shutdown();
				} catch (Exception e) {
					logger.error("上传阿里云OSS出错,关闭client连接出错!!", e);
					throw new BusinessException("上传阿里云OSS出错,关闭client连接出错!!");
				}
			}
			logger.info("文件下载结束...");
		}
		return bos.toByteArray();
	}
	/**
	 * 获取图片在OSS上的url
	 * @param Constant.ossEndPoint 阿里云OSS提供的Constant.ossEndPointOut
	 * @param Constant.ossId 阿里云OSS提供的Constant.ossId
	 * @param Constant.ossKey 阿里云OSS提供的Constant.ossKey
	 * @param Constant.ossBucket 阿里云OSS提供的Constant.ossBucket
	 * @param path 阿里云OSS上保存文件的路径
	 * @return
	 */
	public static String getUrl(String path){
		logger.info("开始获取文件在OSS上的URL。。。。");
		logger.info("Constant.ossEndPoint【{}】,Constant.ossId【{}】,Constant.ossKey【{}】,Constant.ossBucket【{}】,key【{}】", Constant.ossEndPoint, Constant.ossId, Constant.ossKey, Constant.ossBucket, path);
		OSSClient client=null;
		URL url=null;
		try {
		ClientConfiguration conf = new ClientConfiguration();
		conf.setMaxConnections(100);
		conf.setConnectionTimeout(5000);
		conf.setMaxErrorRetry(3);
		conf.setSocketTimeout(2000);

		// 设置URL过期时间为1小时
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		client = createOSSClient();
		// 生成URL
	     url= client.generatePresignedUrl(Constant.ossBucket, path,
				expiration);
		}catch(Throwable e) {
			logger.error("从阿里云OSS获取文件url出错!【{}】", e.getMessage());
			throw new BusinessException("从阿里云OSS获取文件url出错!");
		}finally {
			
			if(null!=client){
				try {
					client.shutdown();
				} catch (Exception e) {
					logger.error("获取OSS上的url出错,关闭client连接出错!!", e);
					throw new BusinessException("获取OSS上的url出错,关闭client连接出错!!");
				}
			}
			logger.info("获取文件URL结束...");
			
		}
		return url.toString();
	}
	
	/**
	 * 获取OSS上影像的列表
	 * @param Constant.ossEndPoint 阿里云OSS提供的Constant.ossEndPointOut
	 * @param Constant.ossId 阿里云OSS提供的Constant.ossId
	 * @param Constant.ossKey 阿里云OSS提供的Constant.ossKey
	 * @param Constant.ossBucket 阿里云OSS提供的Constant.ossBucket
	 * @param ossPath 阿里云OSS上保存文件的路径
	 * @return
	 */
	public static ObjectListing listObjects(String ossPath) {
		logger.info("开始从OSS上获取影像列表。。。。");
		logger.info("Constant.ossEndPoint【{}】,Constant.ossId【{}】,Constant.ossKey【{}】,Constant.ossBucket【{}】,path【{}】", Constant.ossEndPoint, Constant.ossId, Constant.ossKey, Constant.ossBucket,ossPath);
		OSSClient client=null;
		ObjectListing listing1=null;
		try {
		// 初始化OSSClient
		client = createOSSClient();
		logger.info("初始化OSSClient【{}】", client);
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(Constant.ossBucket);
		//		// 递归列出指定目录下的所有文件
		logger.info("ossPath【{}】", ossPath);
		listObjectsRequest.setPrefix(ossPath);//空即为根目录
		// 获取指定bucket下的所有Object信息
		listing1 = client.listObjects(listObjectsRequest);
		}catch(Throwable e) {
			logger.error("从阿里云OSS获取影像列表出错!【{}】", e.getMessage());
			throw new BusinessException("从阿里云OSS获取影像列表出错!");
		}finally {
			if(null!=client){
				try {
					client.shutdown();
				} catch (Exception e) {
					logger.error("从阿里云OSS获取影像列表出错!,关闭client连接出错!!", e);
					throw new BusinessException("从阿里云OSS获取影像列表出错!,关闭client连接出错!!");
				}
			}
			logger.info("从OSS上获取影像列表结束...");
		}	
		return listing1;
	}
	
	
	/**
	 * 删除阿里云OSS文件
	 * @param Constant.ossEndPoint 阿里云OSS提供的Constant.ossEndPoint
	 * @param Constant.ossId 阿里云OSS提供的Constant.ossId
	 * @param Constant.ossKey 阿里云OSS提供的Constant.ossKey
	 * @param Constant.ossBucket 阿里云OSS提供的Constant.ossBucket
	 * @param path　阿里云OSS上保存文件的路径
	 * @return
	 */
	public static void deleteFile(String path) {
		OSSClient client=null;
		try {
			 client = createOSSClient();
		     client.deleteObject(Constant.ossBucket, path);
		} catch (Throwable e) {
			logger.error("从阿里云OSS删除文件出错!【{}】", e.getMessage());
			throw new BusinessException("从阿里云OSS删除文件出错!");
		} finally{
			if(client!=null){
				try {
					client.shutdown();
				} catch (Exception e) {
					logger.error("上传阿里云OSS出错,关闭client连接出错!!", e);
					throw new BusinessException("上传阿里云OSS出错,关闭client连接出错!!");
				}
			}
		}
	}
	
	/**
	 * 初始化一个 OSSClient 
	 * @param Constant.ossEndPoint
	 * @param Constant.ossId
	 * @param Constant.ossKey
	 * @return
	 */
	private static OSSClient createOSSClient() {
		OSSClient client = new OSSClient(Constant.ossEndPoint, Constant.ossId, Constant.ossKey);
		return client;
	}
	
	
	/**
	 * 从OSS上下载信息
	 * @param key 图片在OSS上下的地址
	 * @param bucketName oss的唯一标示
	 * @param subdir 从oss下载到本地的路劲
	 * @return
	 */
	public static void getUploadFile(String key,String subdir) throws Exception
	{
		OSSClient client = new OSSClient(Constant.ossEndPoint,
				Constant.ossId, Constant.ossKey);
		client.getObject(new GetObjectRequest(Constant.ossBucket, key), new File(subdir));
		client.shutdown();
		/*OSSClient client = new OSSClient(Constant.ossEndPoint,
				Constant.ossId, Constant.ossKey);
		OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
		InputStream input = object.getObjectContent();
		String byteStr = "";
		if (input != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));   
			
			StringBuilder sb = new StringBuilder();   
	        String line = null;   
	        try {   
	            while ((line = reader.readLine()) != null) {   
	                sb.append(line + "/n");   
	            }   
	            byteStr = sb.toString();
	        } catch (IOException e) {   
	            throw new Exception("解析OSS二进制流出错！",e);  
	        } 
		}
		return byteStr;*/
	}
	
	public static void main(String[] args) throws Exception{
		/*OSSUtil ossUtil = new OSSUtil();
		String download = ossUtil.getUploadFile("wxhb/UploadImage/20170916/2017091515272700155/2017091616255700074.png",Constant.ossBucket,"E:\\test");
		System.out.println(download);*/
		OSSClient client = new OSSClient(Constant.ossEndPoint,
				Constant.ossId, Constant.ossKey);
		client.getObject(new GetObjectRequest(Constant.ossBucket, "wxhb/UploadImage/20170916/2017091515272700155/2017091616255700074.png"), new File("E:\\test"));
		client.shutdown();
		
	}
	
}
