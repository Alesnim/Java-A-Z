/*
 * Copyright (c) 2020. Created Alesnim
 */

import java.util.*;

/**
 * Bayes classifier class example
 *
 * @see //en.wikipedia.org/wiki/Naive_Bayes_classifier
 * <p>
 * Abstractly, naive Bayes is a conditional probability model:
 * given a problem instance to be classified, represented by a vector
 * x =(x1, ... ,xn) representing some n features (independent variables),
 * it assigns to this instance probabilities
 */
public class NaiveBayes<Category, Feature> {
    /**
     * Dictionary counts features per category
     */
    private Dictionary<NaiveBayes.Category, Dictionary<NaiveBayes.Feature, Integer>> featurePerCategory;


    /**
     * Dictionary count all features
     */
    private Dictionary<NaiveBayes.Feature, Integer> featureCount;

    /**
     * Dictionary count all category
     */
    private Dictionary<NaiveBayes.Category, Integer> categoryCount;


    public Dictionary<NaiveBayes.Category, NaiveBayes.Feature> createData() {
        Hashtable res = new Hashtable<String, Object>();
        return res;
    }

    /**
     * Calculate probability
     *
     * @param category
     * @param features collsections of features objects in category
     * @return probability category in data
     */
    private float featuresProbProd(NaiveBayes.Category category, Collection<NaiveBayes.Feature> features) {
        float productResult = 1.0f;
        for (NaiveBayes.Feature feature : features) {
            productResult *= featureWeightAverage(category, feature);
        }
        return productResult;
    }


    /**
     * @param category
     * @param features
     * @return probability category
     */
    private float categoryProb(NaiveBayes.Category category, Collection<NaiveBayes.Feature> features) {
        float res = (getCategoryCount(category) / categoryCount.size())
                * featuresProbProd(category, features);
        return res;
    }

    /**
     * Get category for data sample in classification
     *
     * @param features
     * @return sets of classification for data
     */
    private SortedSet<Classification<NaiveBayes.Category, Collection<NaiveBayes.Feature>>> categoryProb(Collection<NaiveBayes.Feature> features) {
        Comparator comparator = (Comparator<Classification<NaiveBayes.Category, Collection<NaiveBayes.Feature>>>) (o1, o2) -> {
            float res = Float.compare(o1.getProb(), o2.getProb());
            return (res == 0 && !o1.getCategory().equals(o2.getCategory())) ? -1 : (int) res;
        };
        SortedSet<Classification<NaiveBayes.Category, Collection<NaiveBayes.Feature>>> res;
        res = new TreeSet<>(comparator);
        for (NaiveBayes.Category category : getCategories()) {
            res.add(new Classification<>(category, features, categoryProb(category, features)));
        }
        return res;
    }


    /**
     * Classification method
     *
     * @param features feature sample data
     * @return classification by Bayes Classifier
     */
    public Classification classification(Collection<NaiveBayes.Feature> features) {
        SortedSet<Classification<NaiveBayes.Category, Collection<NaiveBayes.Feature>>> prob = categoryProb(features);
        return (prob.size() > 0) ? prob.last() : null;
    }

    private Iterable<? extends NaiveBayes.Category> getCategories() {
        ArrayList<NaiveBayes.Category> categories = new ArrayList<>();
        for (Iterator<NaiveBayes.Category> it = categoryCount.keys().asIterator(); it.hasNext(); ) {
            categories.add(it.next());
        }
        return Collections.unmodifiableList(categories);
    }


    /**
     * Get count of category
     *
     * @param category
     * @return number of category in data
     */
    private float getCategoryCount(NaiveBayes.Category category) {
        Float res = Float.valueOf(categoryCount.get(category));
        return res;
    }


    private float featureWeightAverage(NaiveBayes.Category category, NaiveBayes.Feature feature) {
        return 0;
    }

    private class Category {
        private String name;
        private int value;

        public Category(String name, int value) {
            this.name = name;
            this.value = value;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    private class Feature {
        private String name;
        private float value;

        public Feature(String name, float value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Feature{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    private class Classification<Category, Collection> {
        private final java.util.Collection<NaiveBayes.Feature> features;
        private NaiveBayes.Category category;
        private float probCategory;

        public Classification(NaiveBayes.Category category, java.util.Collection<NaiveBayes.Feature> features,
                              float probCategory) {
            this.category = category;
            this.features = features;
            this.probCategory = probCategory;
        }

        public float getProb() {
            return 0;
        }

        public NaiveBayes.Category getCategory() {
            return category;
        }
    }
}





