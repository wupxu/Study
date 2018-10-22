package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.ProduceEntity;

public interface ProduceRepository extends JpaRepository<ProduceEntity, Long>,JpaSpecificationExecutor<ProduceEntity> {
	
	@Query("select t from ProduceEntity t where  t.uid=?2 and t.moduleId=?3  and "
			+ "t.produceChannel=?4 and t.status=?5 and t.produceBizid=?1")
	ProduceEntity findProduceEntity(String produceBizid,long uid,int moduleId,int produceChannel,byte status);
	

	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.remainScore=t.produceScore,t.effectiveDate=?5,t.invalidDate=?6,t.status=?7 where t.uid=?1 and t.moduleId=?2 and "
			+ "t.produceChannel=?3 and t.status=?4")
	int update(long uid,int moduleId,int produceChannel,byte fromStatus,long effectiveDate,long invalidDate,byte toStatus);
	
	@Query("select sum(t.produceScore)  from ProduceEntity t  where t.uid=?1 and t.moduleId=?2 and "
			+ "t.produceChannel=?3 and t.status=?4")
	String countUnclaimedScore(long uid,int moduleId,int produceChannel,byte status);
	
	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.remainScore=t.remainScore-?2,t.status=?3 where t.id=?1")
	void update(long id,int score,byte status);
	
	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.produceScore=t.produceScore-?2 where t.id=?1")
	void updateProduceScore(long id,int subtractScore);
	
	/**
	 * 查询有效的未使用的积分
	 * @param uid
	 * @param moduleId
	 * @param status
	 * @param currentDate
	 * @return
	 */
	@Query("select t from ProduceEntity t where  t.uid=?1 and t.moduleId=?2  and "
			+ "t.status=?3 and t.effectiveDate<=?4 and t.invalidDate>=?4 order By t.effectiveDate asc")
	List<ProduceEntity> findValidProduce(long uid,int moduleId,byte status,long currentDate);
	
	@Query("select t from ProduceEntity t where  t.uid=?1 and t.moduleId=?2  and "
			+ "t.status=?3 and t.effectiveDate<=?4 and t.invalidDate>=?4 ")
	Page<ProduceEntity> findValidProduceScore(long uid,int moduleId,byte status,long currentDate, Pageable pageable);
	 
//	int countByUidAndModuleIdAndStatus(long uid,int moduleId,byte status);

	List<ProduceEntity> findByUidAndModuleIdAndProduceChannelAndCTimeBetween(long uid,int moduleId,int produceChannel,long sDate,long eDate);
	
	ProduceEntity findByProduceBizidAndProduceChannel(String produceBizid,int produceChannel);
	
	int countByUidAndModuleIdAndStatusAndProduceChannel(long uid, int moduleId,byte status,int produceChannel);
	
	List<ProduceEntity> findByUidAndModuleIdAndStatusAndProduceChannel(long uid, int moduleId,byte status,int produceChannel);

	/**查询未领取积分*/
	@Query("select t.produceChannel ,sum(t.produceScore) from ProduceEntity t where  t.uid=?1 and t.moduleId=?3  and "
			+ "t.status=?2  group by t.produceChannel")
	List<Object> findUnclaimedByUidAndStatusAndModuleId(long uid, byte status, int code);
	
	/**当天已领取积分*/
	@Query("select t from ProduceEntity t where  t.uid=?1 and t.moduleId = ?3  and "
			+ "t.status=?2 and t.produceChannel = ?4 and t.effectiveDate >= ?5 and t.effectiveDate <= ?6")
	List<ProduceEntity> findReceiveByUidAndStatusAndModuleIdAndEffectiveDate(long uid, byte status, int code,int channelCode,long startDate,long endDate);

	/**按渠道查询待领取乐豆
	List<ProduceEntity> findByUidAndStatusAndProduceChannelAndModuleId(long uid, byte code, int produceChannelCode,int moduleId);
	*/
	
	/**修改生效日期和状态(通过渠道) */
	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.effectiveDate=?2,t.status=?3, t.invalidDate=?7 where t.uid=?1 and t.produceChannel = ?4 and t.status =?6 and t.moduleId=?5 and t.produceBizid= ?8")
	void updateProduceEntityEffectiveDateAndStatusAndBzid(long uid,long date ,byte code,int produceChannelCode,int moduleId,byte status,long invalidDate,String bzid);
	
	/**修改生效日期和状态 */
	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.effectiveDate=?2,t.status=?3, t.invalidDate=?7 where t.uid=?1 and t.produceChannel = ?4 and t.status =?6 and t.moduleId=?5")
	void updateProduceEntityEffectiveDateAndStatus(long uid,long date ,byte code,int produceChannelCode,int moduleId,byte status,long invalidDate);

	/**各渠道待领取乐豆数*/
	@Query("select t.produceChannel ,sum(t.produceScore) from ProduceEntity t where  t.uid=?1 and t.moduleId=?2  and "
			+ "t.status=?3 and t.produceChannel=?4  group by t.produceChannel")
	Object findUnclaimedByUidAndModuleIdAndProduceChannelCode(long uid, int moduleId, byte status,
			int produceChannelCode);



	/* 未领取  */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.status=1 and module_id=?5  group by showDate,t.produce_channel limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllUnclaimedScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.status=1 and module_id=?3 group by showDate,t.produce_channel) cc ",nativeQuery = true)
	long countAllUnclaimedScore(String startTime, String endTime,int moduleId);

	/* 未领取 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.produce_channel=?3 and t.status=1 and module_id=?6  group by showDate,t.produce_channel limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllUnclaimedScore(String startTime, String endTime,int produceChannel,int pageNo,int pageSize,int moduleId);/*  查询时间 消费情况*/

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.produce_channel=?3 and t.status=1 and module_id=?4 group by showDate,t.produce_channel) cc ",nativeQuery = true)
	long countAllUnclaimedScore(String startTime, String endTime,int produceChannel,int moduleId);


	/**
	 * 已发放（未使用+已使用）--无渠道类型
	 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.status in(2,3) and module_id=?5 group by showDate,t.produce_channel limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllGrantScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.status in(2,3) and module_id=?3 group by showDate,t.produce_channel  ) cc",nativeQuery = true)
	long countAllGrantScore(String startTime, String endTime,int moduleId);

	/**
	 * 已发放（未使用+已使用）
	 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.produce_channel=?3 and t.status in(2,3) and module_id=?6  group by showDate,t.produce_channel limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllGrantScore(String startTime, String endTime,int produceChannel,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.produce_channel=?3 and t.status in(2,3) and module_id=?4  group by showDate,t.produce_channel) cc ",nativeQuery = true)
	long countAllGrantScore(String startTime, String endTime,int produceChannel,int moduleId);


	/* 已失效  */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.status=4 and module_id=?5 group by showDate,t.produce_channel limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllInvalidScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.status=4 and module_id=?3 group by showDate,t.produce_channel) cc",nativeQuery = true)
	long countAllInvalidScore(String startTime, String endTime,int moduleId);

	/* 已失效 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.produce_channel=?3 and t.status=4 and module_id=?6  group by showDate,t.produce_channel limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllInvalidScore(String startTime, String endTime,int produceChannel,int pageNo,int pageSize,int moduleId);/*  查询时间 消费情况*/

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.produce_channel=?3 and t.status=4 and module_id=?4  group by showDate,t.produce_channel) cc ",nativeQuery = true)
	long countAllInvalidScore(String startTime, String endTime,int produceChannel,int moduleId);


	/**
	 * 用户已发放全部（）
	 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.status in(2,3) and module_id=?5 group by showDate,t.produce_channel,t.uid limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserGrantScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.status in(2,3) and module_id=?3 group by showDate,t.produce_channel,t.uid  ) cc",nativeQuery = true)
	long countAllByUserGrantScore(String startTime, String endTime,int moduleId);


	/**
	 * 用户已发放全部乐学号（）
	 */
//	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
//			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
//			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <?2 and t.status in(2,3) and module_id=?5 and uid=?6 group by showDate,t.produce_channel,t.uid limit ?3,?4 ",nativeQuery = true)
//	List<ProduceEntity> findAllByUserGrantScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId,long uid);
//
//	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate" +
//			" FROM bp_produce t" +
//			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <?2 and t.status in(2,3) and module_id=?3 and uid=?4 group by showDate,t.produce_channel,t.uid  ) cc",nativeQuery = true)
//	long countAllByUserGrantScore(String startTime, String endTime,int moduleId,long uid);

//	/**
//	 * 用户已发放乐学号（）
//	 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.UID=?3 and t.status in(2,3) and module_id=?6  group by showDate,t.produce_channel,t.uid limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserGrantScore(String startTime, String endTime,long uid,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.effective_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.effective_date/1000)  >= ?1 and FROM_UNIXTIME(t.effective_date/1000) <=?2 and t.UID=?3 and t.status in(2,3) and module_id=?4  group by showDate,t.produce_channel,t.uid) cc ",nativeQuery = true)
	long countAllByUserGrantScore(String startTime, String endTime,long uid,int moduleId);



	/* 用户未领取全部  */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.status=1 and module_id=?5 group by showDate,t.produce_channel,t.uid limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserUnclaimedScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.status=1 and module_id=?3 group by showDate,t.produce_channel,t.uid) cc ",nativeQuery = true)
	long countAllByUserUnclaimedScore(String startTime, String endTime,int moduleId);

	/* 用户未领取 乐学号 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.UID=?3 and t.status=1 and module_id=?6  group by showDate,t.produce_channel,t.uid limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserUnclaimedScore(String startTime, String endTime,long uid,int pageNo,int pageSize,int moduleId);/*  查询时间 消费情况*/

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.CTIME/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.CTIME/1000)  >= ?1 and FROM_UNIXTIME(t.CTIME/1000) <=?2 and t.UID=?3 and t.status=1 and module_id=?4 group by showDate,t.produce_channel,t.uid) cc ",nativeQuery = true)
	long countAllByUserUnclaimedScore(String startTime, String endTime,long uid,int moduleId);


	/* 用户已失效全部  */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.status=4 and module_id=?5 group by showDate,t.produce_channel,t.uid limit ?3,?4 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserInvalidScore(String startTime, String endTime,int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.status=4 and module_id=?3 group by showDate,t.produce_channel,t.uid) cc ",nativeQuery = true)
	long countAllByUserInvalidScore(String startTime, String endTime,int moduleId);

	/* 用户已失效 乐学号 */
	@Query(value = "SELECT t.id,DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate,t.status ,t.produce_channel,t.effective_date,t.invalid_date," +
			"t.CTIME,t.UID as uid,sum(t.produce_score)  as produce_score FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.UID=?3 and t.status=4 and module_id=?6  group by showDate,t.produce_channel,t.uid limit ?4,?5 ",nativeQuery = true)
	List<ProduceEntity> findAllByUserInvalidScore(String startTime, String endTime,long uid,int pageNo,int pageSize,int moduleId);/*  查询时间 消费情况*/

	@Query(value = "select count(cc.showDate) from (SELECT DATE_FORMAT(FROM_UNIXTIME(t.invalid_date/1000),'%Y-%m-%d') as showDate" +
			" FROM bp_produce t" +
			"  where FROM_UNIXTIME(t.invalid_date/1000)  >= ?1 and FROM_UNIXTIME(t.invalid_date/1000) <=?2 and t.UID=?3 and t.status=1 and module_id=?4 group by showDate,t.produce_channel,t.uid) cc ",nativeQuery = true)
	long countAllByUserInvalidScore(String startTime, String endTime,long uid,int moduleId);






	/**
	 * 最后一次行为统计
	 * @param uid
	 * @return
	 */
	@Query(value = "select p.id,max(p.effective_date) as effective_date,max(p.CTIME) as CTIME,p.uid as uid,p.produce_channel as produce_channel from bp_produce p where p.uid=?1 and module_id=?2 group by  p.uid,p.produce_channel",nativeQuery = true)
	List<ProduceEntity> findFinalBehavior(long uid,int moduleId);

	@Query(value = "select p.id,max(p.effective_date) as effective_date,max(p.CTIME) as CTIME,p.uid as uid,p.produce_channel as produce_channel from bp_produce p where  module_id=?3 group by  p.uid,p.produce_channel limit ?1,?2",nativeQuery = true)
	List<ProduceEntity> findFinalBehavior(int pageNo,int pageSize,int moduleId);

	@Query(value = "select count(cc.u) from (select p.uid as u from bp_produce p where module_id=?1 group by p.uid,p.produce_channel) cc ", nativeQuery = true)
	long countFinalBehavior(int moduleId);

	/*  记录用户信息*/
	@Query(value = "SELECT t.produce_channel,sum(t.produce_score) as produce_score,UID,t.effective_date,"
			+ "CASE `STATUS` WHEN ?4 THEN FROM_UNIXTIME(t.effective_date/1000,'%Y-%m-%d') WHEN ?5 THEN FROM_UNIXTIME(t.cTime_date/1000,'%Y-%m-%d') END as date "
			+ "FROM bp_produce t  "
			+ "where t.uid=?3 (t.STATUS= ?4 OR t.STATUS= ?5) AND ((FROM_UNIXTIME(t.effective_date/1000) BETWEEN ?1 and ?2) or (FROM_UNIXTIME(t.cTime/1000)  BETWEEN ?1 and ?2)) group by date,t.UID, t.produce_channel  limit ?6,?7 ",nativeQuery = true)
	List<ProduceEntity> findAllUserScore(String startTime, String endTime,long Uid, long uclaimedStatus,long useStatus,int pageNo,int pageSize);
	

	/*更新状态*/
	@Modifying(clearAutomatically=true)
	@Query("update ProduceEntity t set t.status=?2 where t.id=?1")
	void updateProdeceEntityStatus(long id, byte code);
	
	@Query(value = "select * from bp_produce t where   t.invalid_date < ?1  and t.status=?2 order by invalid_date asc  limit ?3",nativeQuery = true)
	List<ProduceEntity> findProduceEntityInvalidLimit(long date, byte status,int count);

	@Query(value = "select * from bp_produce t where  t.uid=?1 and t.module_id=?2  and t.status=?4 and t.produce_channel=?3 order by rand() limit ?5",nativeQuery = true)
	List<ProduceEntity> findScoreLimit(long uid, int moduleId, int produceChannelCode, byte code,int count);

}
