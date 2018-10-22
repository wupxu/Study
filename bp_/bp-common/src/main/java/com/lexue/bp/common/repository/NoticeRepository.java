package com.lexue.bp.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{

	/**查询公告*/
	NoticeEntity findByStatusAndModuleId(byte code,int moduleId);

	/*@Modifying(clearAutomatically=true)
	@Query("update NoticeEntity t set t.content=?2,t.status=?3,t.updateTime=?4 where t.id=?1")
	void updateById(Long id, String content, int status, long updateTime);*/

	@Query("select t from NoticeEntity t")
	NoticeEntity findNotice();

	NoticeEntity findByModuleId(int moduleId);
	
}
