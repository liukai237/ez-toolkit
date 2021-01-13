package com.iakuil.toolkit;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

class BeanUtilsTest {

    @Test
    void should_copy_non_null_properties() {
        Foo foo = new Foo();
        foo.setName("Harry");

        Bar bar = BeanUtils.copy(foo, Bar.class);
        assertThat(foo.getName(), is(bar.getName()));
    }

    @Test
    void should_copy_necessary_properties() {
        Bar bar = new Bar();
        bar.setName("Zhang San");
        bar.setAge(30);
        bar.setAddr("China");

        Foo foo = BeanUtils.copy(bar, Foo.class);
        assertThat(foo.getName(), is(bar.getName()));
        assertThat(foo.getAge(), is(30));
    }

    @Test
    void should_copy_non_null_and_the_same_type_properties() {
        Foo foo = new Foo();
        foo.setName("Tom");
        foo.setAge(18);

        Baz baz = BeanUtils.copy(foo, Baz.class);
        assertThat(foo.getName(), is(baz.getName()));
        assertThat(baz.getAge(), is(nullValue()));
    }

    @Test
    void should_copy_two_items() {
        Foo foo = new Foo();
        foo.setName("Page");
        foo.setAge(18);

        Foo bar = new Foo();
        bar.setName("George");
        bar.setAge(8);

        List<Bar> family = BeanUtils.copyMany(Arrays.asList(foo, bar), Bar.class);
        assertThat(family, hasSize(2));
    }

    private static class Foo {
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

    private static class Bar {
        private String name;
        private Integer age;
        private String addr;

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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }

    private static class Baz {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}