package com.lexue.bp.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.lexue.bp.common.domain.request.ProduceQueryRequest;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.enums.ProduceStatusEnums;

import java.util.ArrayList;
import java.util.List;

public class ProduceSpecs {
	
	/**
	 * 查询累积明细的条件
	 * @param pqr
	 * @return
	 */
	public static Specification<ProduceEntity> queryProduceDetail(ProduceQueryRequest pqr) {
		return new Specification<ProduceEntity>() {

			@Override
			public Predicate toPredicate(Root<ProduceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicateList = new ArrayList<Predicate>();
				predicateList.add(cb.between(root.<Long>get("effectiveDate"), pqr.getStartDate(), pqr.getEndDate()));
				predicateList.add(cb.and(cb.equal(root.<Integer>get("moduleId"),pqr.getModuleId())));
				predicateList.add(cb.and(cb.equal(root.<Integer>get("status"),ProduceStatusEnums.UNUSED.getCode())));
				if (pqr.getRuleId() != 0) {
					predicateList.add(cb.and(cb.equal(root.<Integer>get("produceChannel"), pqr.getRuleId())));
				}
				if (pqr.getUid() != 0) {
					predicateList.add(cb.and(cb.equal(root.<Long>get("uid"), pqr.getUid())));
				}
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicates = predicateList.toArray(predicates);
				return cb.and(predicates);
			}
			
		};
	}
	

}
