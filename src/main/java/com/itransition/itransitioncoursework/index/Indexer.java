//package com.itransition.itransitioncoursework.index;
////Sevinch Abdisattorova 07/05/2022 3:52 PM
//
//
//import org.hibernate.search.MassIndexer;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//@Transactional
//@Component
//public class Indexer {
//    private static final int THREAD_NUMBER = 4;
//    private EntityManager entityManager;
//
//    public Indexer(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public void indexPersistedData(String indexClassName) throws IndexException {
//
//        try {
//            SearchSession searchSession = Search.session(entityManager);
//
//            Class<?> classToIndex = Class.forName(indexClassName);
//            MassIndexer indexer =
//                    searchSession
//                            .massIndexer(classToIndex)
//                            .threadsToLoadObjects(THREAD_NUMBER);
//
//            indexer.startAndWait();
//        } catch (ClassNotFoundException e) {
//            throw new IndexException("Invalid class " + indexClassName, e);
//        } catch (InterruptedException e) {
//            throw new IndexException("Index Interrupted", e);
//        }
//    }
//}
