package com.lexue.bp.admin.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.lexue.bp.admin.inf.domain.req.StatisticRequest;
import com.lexue.bp.admin.inf.domain.req.UserScoreRequest;
import com.lexue.bp.common.entity.ConsumeEntity;
import com.lexue.bp.common.entity.ProduceEntity;

public class ProduceSpecs {

	/**
	 * 统计需求
	 * 
	 * @param pqr
	 * @return
	 */
	public static Specification<ProduceEntity> queryStatistic(StatisticRequest sr, int status) {
		return new Specification<ProduceEntity>() {

			@Override
			public Predicate toPredicate(Root<ProduceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (status == 2) {
					predicateList.add(cb.between(root.<String>get("showDate"), sr.getStartDate(), sr.getEndDate()));
				}
				if (status == 1) {
					predicateList.add(cb.between(root.<String>get("showDate"), sr.getStartDate(), sr.getEndDate()));
				}
				if (status == 0) {
					predicateList.add(cb.and(cb.equal(root.<Integer>get("produceChannel"), sr.getUid())));

				}
				predicateList.add(cb.and(cb.gt(root.<Integer>get("status"), status)));
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicates = predicateList.toArray(predicates);
				return cb.and(predicates);

			}

		};
	}

	/**
	 * 统计需求
	 * 
	 * @param pqr
	 * @return
	 */
	public static Specification<ConsumeEntity> queryStatisticConsume(StatisticRequest sr, int status) {
		return new Specification<ConsumeEntity>() {
			@Override
			public Predicate toPredicate(Root<ConsumeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				/*
				 * if (status==3) {
				 * predicate.getExpressions().add(cb.between(root.<Long>get("cTime"),
				 * sr.getStartDate(), sr.getEndDate())); }
				 */

				return predicate;
			}

		};
	}

	/**
	 * 统计需求
	 * 
	 * @param pqr
	 * @return
	 */
	public static Specification<ProduceEntity> queryUserScore(UserScoreRequest sr, int status) {
		return new Specification<ProduceEntity>() {

			@Override
			public Predicate toPredicate(Root<ProduceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (status==1) {
				
					predicateList.add(cb.between(root.<Long>get("cTime"), sr.getStartDate(), sr.getEndDate()));
					query.groupBy(root.<Integer>get("uid"),root.<Long>get("cTime"));
				}else  if (status==2) {
					predicateList.add(cb.between(root.<Long>get("effectiveDate"), sr.getStartDate(), sr.getEndDate()));
					query.groupBy(root.<Integer>get("uid"),root.<Long>get("effectiveDate"));
				}
				predicateList.add(cb.and(cb.equal(root.<Integer>get("uid"), sr.getUid())));
				predicateList.add(cb.and(cb.equal(root.<Integer>get("status"), status)));
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicates = predicateList.toArray(predicates);
				return cb.and(predicates);

			}

		};
	}

}
