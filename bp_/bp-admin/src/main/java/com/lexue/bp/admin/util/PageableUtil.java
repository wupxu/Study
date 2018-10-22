package com.lexue.bp.admin.util;

import java.util.ArrayList;
import java.util.List;

import com.lexue.bp.common.domain.PageAsk;
import com.lexue.bp.common.domain.PageReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

import com.lexue.bp.admin.inf.domain.res.UserManagementResponse;



public class PageableUtil {
	
	public static Pageable generatePageable(PageAsk page) {
		if (page == null) {
			return new PageRequest(0, 20);
		} else {
			if (StringUtils.isEmpty(page.getSort())) {
				return new PageRequest(page.getPage(),page.getSize()==0?20:page.getSize());
			} else {
				List<Order> orders = new ArrayList<>();
				String[] multi = page.getSort().split("&");
				for (String orderTmp:multi) {
					String[] tmp = orderTmp.split("\\,");
					if (tmp[1].equalsIgnoreCase("desc")) {
						orders.add(new Order(Sort.Direction.DESC, tmp[0]));
					} else if (tmp[1].equalsIgnoreCase("asc")) {
						orders.add(new Order(Sort.Direction.ASC, tmp[0]));
					}
				}
				Sort sort = new Sort(orders); 
				return new PageRequest(page.getPage(),page.getSize()==0?20:page.getSize(),sort);
			}
		}
	}
	
	
	public static <T> PageReply<T> pageToPageReply(Page<T> source) {
		PageReply<T>  dest = new PageReply<T>();
		dest.setDatas(source.getContent());
		dest.setNext(source.hasNext());
		dest.setNumber(source.getNumber());
		dest.setPrevious(source.hasPrevious());
		dest.setSize(source.getSize());
		dest.setTotalElements(source.getTotalElements());
		dest.setTotalPages(source.getTotalPages());
		return dest;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> PageReply<UserManagementResponse>  pageToPageReplyUserManagement(Page<T> source) {
		PageReply<T>  dest = new PageReply<T>();
		dest.setDatas(source.getContent());
		dest.setNext(source.hasNext());
		dest.setNumber(source.getNumber());
		dest.setPrevious(source.hasPrevious());
		dest.setSize(source.getSize());
		dest.setTotalElements(source.getTotalElements());
		dest.setTotalPages(source.getTotalPages());
		return (PageReply<UserManagementResponse>) dest;
	}
	
	

}
 