import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

class ArrayList {
    final int initialCapacity = 10;
    Patient[] patients;
    int size;
    int capacity;

    ArrayList() {
        this.capacity = initialCapacity;
        this.size = 0;
        this.patients = new Patient[capacity];
    }

    void add(Patient patient) {
        // double the capacity of the ArrayList when size reached the capacity
        if (this.capacity <= this.size) {
            this.capacity *= 2;
            Patient[] newPatients = new Patient[capacity];
            int newIndex = 0;
            for (Patient patientIn: patients) {
                newPatients[newIndex] = patientIn;
                newIndex++;
            }
            this.patients = newPatients;
        }
        this.patients[size] = patient;
        size++;
    }

    Patient get(int index) {
        return this.patients[index];
    }

    int size() {
        return size;
    }

    // Reference Data Structures and Algorithms in Java Page 553
    public ArrayList quickSort(ArrayList origin) {
        if (origin.size() <= 1) {
            return origin;
        } else {
            // choose a pivot
            int mid = origin.size() / 2;
            Patient pivot = origin.get(mid);
            ArrayList smaller = new ArrayList();
            ArrayList greater = new ArrayList();
            //check each patient belongs to which group
            for (int i = 0; i < origin.size(); i++) {
                Patient patient = origin.get(i);
                if (i != mid) {
                    if (patient.time(patient.getTime()) < pivot.time(pivot.getTime())) {
                        smaller.add(patient);
                    } else if (patient.time(patient.getTime()) > pivot.time(pivot.getTime())) {
                        greater.add(patient);
                    } else {
                        if (i < mid) {
                            smaller.add(patient);
                        } else {
                            greater.add(patient);
                        }
                    }
                }
            }
            // Combine small part and large part together
            ArrayList ans = new ArrayList();
            ArrayList small = quickSort(smaller);
            ArrayList large = quickSort(greater);
            for (int i = 0; i < small.size(); i++) {
                ans.add(small.get(i));
            }
            ans.add(pivot);
            for (int i = 0; i < large.size(); i++) {
                ans.add(large.get(i));
            }
            return ans;
        }
    }
}

public class Hospital2 extends HospitalBase {
    public static ArrayList patientsLine;

    public Hospital2() {
        /* Add your code here! */
        this.patientsLine = new ArrayList();
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        int hour = Integer.parseInt(patient.getTime().split(":", 2)[0]);
        if (hour >= 18 || hour < 8 || hour == 12) {
            return false;
        } else {
            patientsLine.add((Patient) patient);
            return true;
        }
    }

    @Override
    public Iterator<PatientBase> iterator() {
        /* Add your code here! */
        this.patientsLine = this.patientsLine.quickSort(this.patientsLine);
        return new Iterator<PatientBase>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (index < patientsLine.size()) {
                    return patientsLine.get(index) != null;
                }
                return false;
            }

            @Override
            public PatientBase next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return patientsLine.get(index++);
            }
        };
    }
    /* Add any extra functions below */
}
