package com.canyinghao.greenlibrary;

/**
 * Created by yangjian on 15/5/30.
 */


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * ����ΪGreenDao�������Dao�ļ�
 */
public class MyDaoGenerator {

    //�����ļ����ɵ����·��
    public static final String DAO_PATH = "../app/src/main/java-gen";
    //�����ļ��İ���
    public static final String PACKAGE_NAME = "com.canyinghao.greenlibrary";
    //���ݿ�İ汾��
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addCache(schema, "MusicBean");

        //����Dao�ļ�·��
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }

    /**
     * ��Ӳ�ͬ�Ļ����
     * @param schema
     * @param tableName
     */
    private static void addCache(Schema schema, String tableName) {

        Entity joke = schema.addEntity(tableName);

        //����id������
        joke.addIdProperty().primaryKey().autoincrement();
        //������
        joke.addStringProperty("result");
        //ҳ��
        joke.addIntProperty("page");
        //����ʱ�䣬��ʱ����
        joke.addLongProperty("time");

    }
}