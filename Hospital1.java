import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Hospital1 extends HospitalBase {
    public final int minPerHour = 60;
    public final int hours = (12 - 8) + (18 - 13);
    public final int sessionLength = 20;
    public Patient[] patientArrangement;
    public Hospital1() {
        // calcculate the number of time slot for hospital 1
        this.patientArrangement = new Patient[hours*minPerHour/sessionLength];
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        // check if patient's appointment time is within the range
        int hour = Integer.parseInt(patient.getTime().split(":", 2)[0]);
        int minute = Integer.parseInt(patient.getTime().split(":", 2)[1]);
        boolean canAdd;
        int patientIndex = this.getIndex((Patient) patient);
        if (hour >= 18 || hour < 8 || hour == 12) {
            canAdd = false;
        } else if (minute == 0 || minute == 20 || minute == 40) {
            canAdd = true;
        } else {
            canAdd = false;
        }
        // check if the time slot is occupied
        if (canAdd && this.patientArrangement[patientIndex] == null) {
            this.patientArrangement[patientIndex] = (Patient) patient;
            return true;
        } else {
            return false;
        }
    }

    // Reference Data Structures and Algorithms in Java Page 285
    @Override
    public Iterator<PatientBase> iterator() {
        return new Iterator<PatientBase>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                while (index < patientArrangement.length) {
                    if (patientArrangement[index] != null) {
                        return true;
                    }
                    index++;
                }
                return false;

            }
            @Override
            public PatientBase next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return patientArrangement[index++];
            }
        };
    }

    /* Add any extra functions below */
    /**
     * Return the index for the patient in the Array
     * @param patient the patient we want to calculate index for
     * @return the index for the patient in the Array according to
     * patient's appointment time
     */
    private int getIndex(Patient patient) {
        int index;
        int time = patient.time(patient.getTime());
        if (time < patient.time("13:00")) {
            index = (time - patient.time("08:00")) / sessionLength;
        } else {
            index = (time - patient.time("08:00")-minPerHour) / sessionLength;
        }
        return index;
    }
}