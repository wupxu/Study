package com.lexue.bp.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.lexue.bp.common.domain.request.ScoreChangeRequest;
import com.lexue.bp.common.domain.request.UserManagementQueryRequest;
import com.lexue.bp.common.entity.DataChangeScoreEntity;
import com.lexue.bp.common.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserManagementSpecs {

	/**
	 * 查询用户管理的条件
	 * @param pqr
	 * @return
	 */
	public static Specification<UserEntity> queryUserManagementDetail(UserManagementQueryRequest umqr) {
		return new Specification<UserEntity>() {

			@Override
			public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				predicateList.add(cb.equal(root.<Integer>get("moduleId"), umqr.getModuleId()));

				if(umqr.getEndScore() != 0){
					predicateList.add(cb.between(root.get("totalRemain"), umqr.getStartScore(), umqr.getEndScore()));
				}
				if (umqr.getUid() != 0) {
					predicateList.add(cb.equal(root.<Long>get("uid"), umqr.getUid()));
				}
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicates = predicateList.toArray(predicates);
				return cb.and(predicates);
			}
			
		};
	}

	public static Specification<DataChangeScoreEntity> queryChangeScoreDetail(ScoreChangeRequest scoreChangeRequest) {
		return new Specification<DataChangeScoreEntity>() {

			@Override
			public Predicate toPredicate(Root<DataChangeScoreEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				predicateList.add(cb.between(root.get("updateTime"), scoreChangeRequest.getStartDate(), scoreChangeRequest.getEndDate()));

				predicateList.add(cb.equal(root.<Integer>get("moduleId"), scoreChangeRequest.getModuleId()));

				if (scoreChangeRequest.getUid() != 0) {
					predicateList.add(cb.equal(root.<Long>get("uid"), scoreChangeRequest.getUid()));
				}
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicates = predicateList.toArray(predicates);
				return cb.and(predicates);
			}
			
		};
	}
	
}