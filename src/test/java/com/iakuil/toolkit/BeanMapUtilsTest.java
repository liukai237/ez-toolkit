package com.iakuil.toolkit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BeanMapUtilsTest {

    @Test
    void should_copy_all_properties() {
        Foo foo = new Foo();
        foo.setName("Tom");
        foo.setAge(18);

        Map<String, Object> result = BeanMapUtils.beanToMap(foo);
        assertThat(result, notNullValue());
        assertThat(result.get("name"), is("Tom"));
        assertThat(result.get("age"), is(18));
    }

    @Test
    void should_copy_non_null_properties() {
        Foo foo = new Foo();
        foo.setName(null);
        foo.setAge(18);

        Map<String, Object> result = BeanMapUtils.beanToMap(foo, true);
        assertThat(result, notNullValue());
        assertThat(result, not(hasKey("name")));
        assertThat(result.get("age"), is(18));
    }

    @Test
    void should_copy_all_properties_from_entries() {
        Map<String, Object> srcMap = new HashMap<>();
        srcMap.put("name", "Green");
        srcMap.put("age", 36);

        Foo foo = BeanMapUtils.mapToBean(srcMap, Foo.class);
        assertThat(foo, notNullValue());
        assertThat(foo, hasProperty("name", is("Green")));
        assertThat(foo, hasProperty("age", is(36)));
    }

    @Test
    void should_copy_and_override_all_properties_with_entries() {
        Map<String, Object> srcMap = new HashMap<>();
        srcMap.put("name", "Polly");
        srcMap.put("age", 2);

        Foo foo = new Foo();
        foo.setAge(1);
        foo = BeanMapUtils.mapToBean(srcMap, foo);
        assertThat(foo, notNullValue());
        assertThat(foo, hasProperty("name", is("Polly")));
        assertThat(foo, hasProperty("age", is(2)));
    }

    @Test
    void should_copy_all_entries_to_objects() {
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Polly");
        map1.put("age", 2);
        maps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Harry");
        map2.put("age", 18);
        maps.add(map2);

        List<Foo> objs = BeanMapUtils.mapsToObjects(maps, Foo.class);
        assertThat(objs, hasSize(2));
        assertThat(objs, everyItem(instanceOf(Foo.class)));
        assertThat(objs, hasItem(allOf(hasProperty("name", equalTo("Polly")), hasProperty("age", equalTo(2)))));
        assertThat(objs, hasItem(allOf(hasProperty("name", equalTo("Harry")), hasProperty("age", equalTo(18)))));
    }

    @Test
    void should_copy_all_objects_to_maps() {
        List<Foo> objs = new ArrayList<>();
        Foo foo1 = new Foo();
        foo1.setName("Tom");
        foo1.setAge(24);
        objs.add(foo1);
        Foo foo2 = new Foo();
        foo2.setName("Jack");
        foo2.setAge(8);
        objs.add(foo2);

        List<Map<String, Object>> maps = BeanMapUtils.objectsToMaps(objs);
        assertThat(maps, hasSize(2));
        Map<String, Object> map1 = maps.get(0);
        assertThat(map1, hasEntry("name", "Tom"));
        assertThat(map1, hasEntry("age", 24));
        Map<String, Object> map2 = maps.get(1);
        assertThat(map2, hasEntry("name", "Jack"));
        assertThat(map2, hasEntry("age", 8));
    }

    public static class Foo {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}