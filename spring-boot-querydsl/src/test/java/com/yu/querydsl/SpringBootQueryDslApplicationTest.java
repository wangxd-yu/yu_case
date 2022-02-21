package com.yu.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.Group;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.querydsl.modules.dept.domain.DeptDO;
import com.yu.querydsl.modules.dept.domain.QDeptDO;
import com.yu.querydsl.modules.dept.dto.*;
import com.yu.querydsl.modules.dept.repository.DeptRepository;
import com.yu.querydsl.modules.user.domain.QUserDO;
import com.yu.querydsl.modules.user.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2022-02-07 18:37
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles
@SpringBootTest(classes = SpringBootQueryDslApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootQueryDslApplicationTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private DeptRepository deptRepository;

    private JPAQueryFactory jpaQueryFactory;

    QDeptDO qDeptDO = QDeptDO.deptDO;
    QUserDO qUserDO = QUserDO.userDO;

    @PostConstruct
    private void init() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Test
    @Order(1)
    @DisplayName("【查询】-单表查询")
    void querySingle() {
        List<UserDO> userDOList = jpaQueryFactory.selectFrom(qUserDO).fetch();
        Assertions.assertEquals(userDOList.size(), 4);
    }

    @Test
    @Order(2)
    @DisplayName("【查询】连表查询-cross join (from)")
    void queryMulFrom() {
        List<UserDO> userDOList = jpaQueryFactory
                .select(qUserDO)
                .from(qUserDO, qDeptDO)
                .where(qUserDO.deptId.eq(qDeptDO.id))
                .fetch();
        Assertions.assertEquals(userDOList.size(), 4);
    }

    @Test
    @Order(3)
    @DisplayName("【查询】-联表查询-方法2：join")
    void queryMulJoin() {
        List<UserDO> userDOList2 = jpaQueryFactory
                .select(qUserDO)
                .from(qUserDO)
                .innerJoin(qDeptDO).on(qUserDO.deptId.eq(qDeptDO.id))
                .fetch();
        Assertions.assertEquals(userDOList2.size(), 4);
    }

    @Test
    @Order(4)
    @DisplayName("【查询】-条件查询-eq")
    void queryEq() {
        List<UserDO> userDOList = jpaQueryFactory.selectFrom(qUserDO)
                .where(qUserDO.id.eq(1L))
                .fetch();
        Assertions.assertEquals(userDOList.size(), 1);
    }

    @Test
    @Order(5)
    @DisplayName("【查询】-条件查询-like")
    void queryLike() {
        List<DeptDO> deptDOList = jpaQueryFactory.selectFrom(qDeptDO)
                .where(qDeptDO.name.like("开发" + "%"))
                .fetch();
        Assertions.assertEquals(deptDOList.size(), 2);
    }

    @Test
    @Order(6)
    @DisplayName("【查询】-条件查询-startWith")
    void queryStartWith() {
        List<DeptDO> deptDOList = jpaQueryFactory.selectFrom(qDeptDO)
                .where(qDeptDO.name.startsWith("开发"))
                .fetch();
        Assertions.assertEquals(deptDOList.size(), 2);
    }

    @Test
    @Order(7)
    @DisplayName("【查询】-条件查询-endsWith")
    void queryEndWith() {
        List<DeptDO> deptDOList = jpaQueryFactory.selectFrom(qDeptDO)
                .where(qDeptDO.name.endsWith("一部"))
                .fetch();
        Assertions.assertEquals(deptDOList.size(), 2);
    }

    @Test
    @Order(7)
    @DisplayName("【子查询】-SubQueries")
    void subQueries() {
        List<DeptDO> deptDOList = jpaQueryFactory.selectFrom(qDeptDO)
                .where(qDeptDO.id.eq(
                        JPAExpressions.select(qDeptDO.id.max()).from(qDeptDO)
                )).fetch();
        Assertions.assertEquals(deptDOList.size(), 1);
    }

    @Test
    @Order(101)
    @DisplayName("【新增】-insert")
    void insert() {
        DeptDO deptDO = new DeptDO();
        deptDO.setId(5L);
        deptDO.setSort(5);
        deptDO.setName("插入部门");
        jpaQueryFactory.insert(qDeptDO).columns(qDeptDO.id, qDeptDO.name, qDeptDO.sort).values(5L, "11", 3);
    }

    @Test
    @Order(201)
    @DisplayName("【更新】-update")
    @Transactional
    void update() {
        Long num = jpaQueryFactory.update(qDeptDO).set(qDeptDO.name, "更新值").where(qDeptDO.id.eq(1L)).execute();
        Assertions.assertEquals(num, 1L);
    }

    @Test
    @Order(301)
    @DisplayName("【删除】-delete")
    @Transactional
    void delete() {
        Long num = jpaQueryFactory.delete(qDeptDO).where(qDeptDO.id.eq(1L)).execute();
        Assertions.assertEquals(num, 1L);
    }

    @Test
    @Order(400)
    @DisplayName("【结果处理】-返回多列-Tuple")
    void resultHandingTuple() {
        // 查询
        List<Tuple> result = jpaQueryFactory.select(qDeptDO.name, qDeptDO.sort).from(qDeptDO).fetch();

        // 结果处理
        for (Tuple row : result) {
            System.out.println("name：" + row.get(qDeptDO.name));
            System.out.println("sort：" + row.get(qDeptDO.sort));
        }
    }

    @Test
    @Order(410)
    @DisplayName("【结果处理】-对象填充-属性有set方法")
    void resultHandingBeanWithSet() {
        // 查询
        List<DeptWithSetterDTO> result = jpaQueryFactory
                .select(Projections.bean(DeptWithSetterDTO.class, qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptWithSetterDTO dto : result) {
            System.out.println(dto.toString());
        }
    }

    @Test
    @Order(411)
    @DisplayName("【结果处理】-对象填充-属性无set方法")
    void resultHandingBeanNoSet() {
        // 查询
        List<DeptNoSetterDTO> result = jpaQueryFactory
                .select(Projections.fields(DeptNoSetterDTO.class, qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptNoSetterDTO dto : result) {
            System.out.println(dto.toString());
        }
    }

    @Test
    @Order(420)
    @DisplayName("【结果处理】-构造函数")
    void resultHandingConstructor() {
        // 查询
        List<DeptConstructorDTO> result = jpaQueryFactory
                .select(Projections.constructor(DeptConstructorDTO.class, qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptConstructorDTO dto : result) {
            System.out.println(dto.toString());
        }
    }

    @Test
    @Order(421)
    @DisplayName("【结果处理】-构造函数-dto使用注解")
    void resultHandingConstructorDtoUseAnnotation() {
        // 查询
        List<DeptConstructorUseAnnotationDTO> result = jpaQueryFactory
                .select(new QDeptConstructorUseAnnotationDTO(qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptConstructorUseAnnotationDTO dto : result) {
            System.out.println(dto.toString());
        }
    }

    @Test
    @Order(422)
    @DisplayName("【结果处理】-构造函数-entity使用注解")
    void resultHandingConstructorEntityUseAnnotation() {
        // 查询
        List<DeptDO> result1 = jpaQueryFactory
                .select(QDeptDO.create(qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptDO dto : result1) {
            System.out.println(dto.toString());
        }

        // 查询
        List<DeptDO> result2 = jpaQueryFactory
                .select(QDeptDO.create(qDeptDO.id, qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptDO dto : result2) {
            System.out.println(dto.toString());
        }

        // 查询
        List<DeptDO> result3 = jpaQueryFactory
                .select(Projections.constructor(DeptDO.class, qDeptDO.id, qDeptDO.name, qDeptDO.sort))
                .from(qDeptDO).fetch();

        // 结果处理
        for (DeptDO dto : result3) {
            System.out.println(dto.toString());
        }
    }

    @Test
    @Order(430)
    @DisplayName("【结果处理】-结果聚合-List")
    void resultHandingAggregationList() {
        // 查询
        Map<Long, List<UserDO>> listMap = jpaQueryFactory
                .from(qDeptDO, qUserDO)
                .where(qDeptDO.id.eq(qUserDO.deptId))
                .transform(GroupBy.groupBy(qDeptDO.id).as(GroupBy.list(qUserDO)));

        // 结果处理
        Assertions.assertEquals(listMap.get(1L).size(), 2);
        Assertions.assertEquals(listMap.get(2L).size(), 2);
    }

    @Test
    @Order(431)
    @DisplayName("【结果处理】-结果聚合-Group")
    void resultHandingAggregationGroup() {
        // 查询
        Map<Long, Group> listMap = jpaQueryFactory
                .from(qDeptDO, qUserDO)
                .where(qDeptDO.id.eq(qUserDO.deptId))
                .transform(GroupBy.groupBy(qDeptDO.id)
                        .as(qUserDO.name, GroupBy.list(qUserDO.id), GroupBy.set(qUserDO.username))
                );

        // 结果处理
        Assertions.assertEquals(listMap.get(1L).getOne(qUserDO.name), "张三");
        Assertions.assertEquals(listMap.get(2L).getList(qUserDO.id), Arrays.asList(3L, 4L));
        Assertions.assertEquals(listMap.get(2L).getSet(qUserDO.username).size(), 2);
    }

    @Test
    @Order(432)
    @DisplayName("【结果处理】-结果聚合-Map")
    void resultHandingAggregationGroupMap() {
        // 查询
        Map<Long, Group> listMap = jpaQueryFactory
                .from(qDeptDO, qUserDO)
                .where(qDeptDO.id.eq(qUserDO.deptId))
                .transform(GroupBy.groupBy(qDeptDO.id).as(qUserDO.name, GroupBy.map(qUserDO.name, qUserDO.username)));

        // 结果处理
        Assertions.assertEquals(listMap.get(1L).getOne(qUserDO.name), "张三");
        Assertions.assertEquals(listMap.get(1L).getMap(qUserDO.name, qUserDO.username).size(), 2);
    }

    @Test
    @Order(433)
    @DisplayName("【结果处理】-结果聚合-Group-bean")
    void resultHandingAggregationGroupProjectionAsBean() {
        // 查询
        Map<Long, DeptDO> listMap = jpaQueryFactory
                .from(qDeptDO, qUserDO)
                .where(qDeptDO.id.eq(qUserDO.deptId))
                .transform(GroupBy.groupBy(qUserDO.deptId).as(
                        Projections.bean(DeptDO.class, qDeptDO.id, qDeptDO.name, qDeptDO.sort)
                ));

        // 结果处理
        DeptDO deptDO = listMap.get(1L);
        Assertions.assertNotNull(deptDO);
        Assertions.assertEquals(1L, deptDO.getId());
    }
}