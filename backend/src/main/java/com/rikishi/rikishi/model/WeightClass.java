package com.rikishi.rikishi.model;

/**
 * According to the <a href="http://www.ifs-sumo.org/ifs-weight-class.html">ifs-sumo.org</a>,
 * weight class is represented not only by weight range, but also by sex and age range.
 *
 * @param id     The weight class id.
 * @param name   The weight class name.
 * @param sex    The sex.
 * @param weight The weight range.
 * @param age    The age range.
 */
public record WeightClass(
    long id,
    String name,
    Sex sex,
    Range<Double> weight,
    Range<Integer> age
) {
    public boolean isValid(Sex sex, double weight, int age) {
        return this.sex.equals(sex) && this.weight.contains(weight) && this.age.contains(age);
    }

    public boolean isValid(User user) {
        return isValid(user.sex(), user.weight(), user.age());
    }
}
