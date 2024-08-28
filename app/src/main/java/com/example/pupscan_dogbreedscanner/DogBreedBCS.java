package com.example.pupscan_dogbreedscanner;

public class DogBreedBCS {

    public static String[] breedNames = {
            "Affenpinscher", "Afghan Hound", "Airedale Terrier", "Akita", "Alaskan Malamute",
            "American Eskimo Dog", "American Foxhound", "American Staffordshire Terrier",
            "American Water Spaniel", "Anatolian Shepherd Dog", "Australian Cattle Dog",
            "Australian Shepherd", "Australian Terrier", "Basenji", "Basset Hound", "Beagle",
            "Bearded Collie", "Beauceron", "Bedlington Terrier", "Belgian Malinois",
            "Belgian Sheepdog", "Belgian Tervuren", "Bernese Mountain Dog", "Bichon Frise",
            "Black and Tan Coonhound", "Black Russian Terrier", "Bloodhound", "Bluetick Coonhound",
            "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier", "Bouvier des Flandres",
            "Boxer", "Boykin Spaniel", "Briard", "Brittany", "Brussels Griffon", "Bull Terrier",
            "Bulldog", "Bullmastiff", "Cairn Terrier", "Canaan Dog", "Cane Corso",
            "Cardigan Welsh Corgi", "Cavalier King Charles Spaniel", "Chesapeake Bay Retriever",
            "Chihuahua", "Chinese Crested", "Chinese Shar-Pei", "Chow Chow", "Clumber Spaniel",
            "Cocker Spaniel", "Collie", "Curly-Coated Retriever", "Dachshund", "Dalmatian",
            "Dandie Dinmont Terrier", "Doberman Pinscher", "Dogue de Bordeaux",
            "English Cocker Spaniel", "English Setter", "English Springer Spaniel",
            "English Toy Spaniel", "Entlebucher Mountain Dog", "Field Spaniel", "Finnish Spitz",
            "Flat-Coated Retriever", "French Bulldog", "German Pinscher", "German Shepherd Dog",
            "German Shorthaired Pointer", "German Wirehaired Pointer", "Giant Schnauzer",
            "Glen of Imaal Terrier", "Golden Retriever", "Gordon Setter", "Great Dane",
            "Great Pyrenees", "Greater Swiss Mountain Dog", "Greyhound", "Havanese", "Ibizan Hound",
            "Icelandic Sheepdog", "Irish Red and White Setter", "Irish Setter", "Irish Terrier",
            "Irish Water Spaniel", "Irish Wolfhound", "Italian Greyhound", "Japanese Chin",
            "Keeshond", "Kerry Blue Terrier", "Komondor", "Kuvasz", "Labrador Retriever",
            "Lakeland Terrier", "Leonberger", "Lhasa Apso", "Lowchen", "Maltese",
            "Manchester Terrier", "Mastiff", "Miniature Schnauzer", "Neapolitan Mastiff",
            "Newfoundland", "Norfolk Terrier", "Norwegian Buhund", "Norwegian Elkhound",
            "Norwegian Lundehund", "Norwich Terrier", "Nova Scotia Duck Tolling Retriever",
            "Old English Sheepdog", "Otterhound", "Papillon", "Parson Russell Terrier",
            "Pekingese", "Pembroke Welsh Corgi", "Petit Basset Griffon Vendeen", "Pharaoh Hound",
            "Plott", "Pointer", "Pomeranian", "Poodle", "Portuguese Water Dog", "Saint Bernard",
            "Silky Terrier", "Smooth Fox Terrier", "Tibetan Mastiff", "Welsh Springer Spaniel",
            "Wirehaired Pointing Griffon", "Xoloitzcuintli", "Yorkshire Terrier"
    };

    public static String[] averageWeightsMaleKg = {
            "3.86", "24.95", "27.22", "52.16", "38.56",
            "6.58", "30.61", "28.35", "14.74", "58.97",
            "19.32", "26.13", "7.94", "10.89", "23.81",
            "11.34", "22.68", "40.82", "9.07", "31.75",
            "29.48", "29.48", "44.27", "6.80", "39.69",
            "47.63", "45.36", "30.61", "19.32", "6.35",
            "40.82", "8.39", "40.82", "29.48", "6.35",
            "14.74", "9.07", "28.35", "26.13", "3.86", // Note: I corrected the typo here, changing "3.63-4.54" to "3.86"
            "22.68", "18.14", "49.90", "6.35", "24.18", // Note: I added a corrected value for "20.41-24.95"
            "0.0", "13.61", "5.90", "32.88", // Note: I corrected the typo here, changing "29.48-36.29" to "32.88"
            "11.34", "21.77", "10.89", "18.14", "29.48",
            "13.61", "15.88", "9.07", "14.51", "24.95",
            "31.75", "11.34", "15.88", "11.79", "22.68",
            "22.68", "20.41", "15.88", "18.14", "14.97",
            "0.0", "0.0",
            "54.43", "10.89", "0.0", "49.90", "5.44",
            "0.0", "6.80", "11.34", "22.68", "20.41",
            "15.88", "15.88", "9.07", "15.88", "18.14",
            "29.48", "20.41", "15.88", "18.14", "18.14",
            "4.08", "15.88", "11.34", "6.80", "4.54",
            "3.18", "3.18", "15.88", "15.88", "13.61",
            "6.80", "7.71", "4.08", "15.88", "13.61",
            "4.54", "6.80", "13.61", "3.18"
    };


    public static String[] averageWeightsFemaleKg = {
            "3.86", "24.95", "27.22", "38.56", "34.02",
            "6.58", "28.35", "21.50", "14.74", "45.36",
            "19.32", "21.50", "7.94", "10.89", "23.81",
            "11.34", "22.68", "40.82", "9.07", "31.75",
            "28.35", "28.35", "37.51", "6.80", "39.69",
            "47.63", "38.56", "24.95", "24.95", "5.78",
            "32.93", "8.39", "40.82", "29.48", "8.39",
            "14.74", "9.07", "28.35", "21.50", "3.86",
            "18.14", "49.90", "49.90", "5.90", "20.41",
            "0.0", "13.61", "5.90", "32.88",
            "11.34", "21.77", "10.89", "18.14", "29.48",
            "13.61", "15.88", "9.07", "14.51", "24.95",
            "31.75", "11.34", "15.88", "11.79", "22.68",
            "22.68", "20.41", "15.88", "18.14", "14.97",
            "0.0", "0.0",
            "54.43", "10.89", "0.0", "49.90", "5.44",
            "0.0", "6.80", "11.34", "22.68", "20.41",
            "15.88", "15.88", "9.07", "15.88", "18.14",
            "29.48", "20.41", "15.88", "18.14", "18.14",
            "4.08", "15.88", "11.34", "6.80", "4.54",
            "3.18", "3.18", "15.88", "15.88", "13.61",
            "6.80", "7.71", "4.08", "15.88", "13.61",
            "4.54", "6.80", "13.61", "3.18"
    };


    public static class BCSCalculator {

        public static double calculateBCS(String selectedBreed, String selectedGender, double currentWeight) {
            // Find the average weight for the selected breed and gender
            String[] weightsArray = (selectedGender.equalsIgnoreCase("Male")) ? DogBreedBCS.averageWeightsMaleKg : DogBreedBCS.averageWeightsFemaleKg;

            double averageWeight = findAverageWeight(selectedBreed, weightsArray);

            // Calculate BCS based on weight difference
            double weightDifference = currentWeight - averageWeight;

            // Map the weight difference to the 1-5 BCS range
            double bcs = mapToBCSRange(weightDifference);

            return bcs;
        }

        private static double mapToBCSRange(double weightDifference) {
            // Map the weight difference to the 1-5 BCS range
            double scaleFactor = 0.25;
            double bcs = 3.0 + (weightDifference * scaleFactor);

            // Ensure BCS is within valid range (1.0 to 5.0)
            bcs = Math.max(1.0, Math.min(5.0, bcs));

            return bcs;
        }



        private static double findAverageWeight(String selectedBreed, String[] weightsArray) {
            for (int i = 0; i < DogBreedBCS.breedNames.length; i++) {
                if (DogBreedBCS.breedNames[i].equalsIgnoreCase(selectedBreed)) {
                    try {
                        // Extract the numeric weight from the string
                        String weightString = weightsArray[i];
                        double weight = Double.parseDouble(weightString.split(" ")[0]);
                        return weight;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Return a default value if breed is not found
            return 0.0;
        }
    }

}

