import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

class PatientNode{
    public Patient patient;
    public PatientNode next;
}
class PatientLinkedList{
    int size;
    PatientNode head;
    public PatientLinkedList() {
        this.size = 0;
        this.head = null;
    }

    public void insertNode(Patient item) {
        PatientNode node = new PatientNode();
        node.patient = item;
        PatientNode current = this.head;
        //insert to head if it's null
        if (this.head == null) {
            this.head = node;
            this.head.next = null;
            this.size = 1;
        } else {
            // insert to the tail
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
            node.next = null;
            this.size += 1;
        }
    }

    public int getListSize(){
        return size;
    }

    public Patient findMin() {
        if (head == null) {
            return null;
        }
        if (size == 1) {
            size = 0;
            return head.patient;
        }
        PatientNode min = head;
        PatientNode minPre = null;
        PatientNode current = min.next;
        PatientNode previous = head;
        // loop the linkedList to find the min value
        while(current != null) {
            Patient currentMin = min.patient;
            Patient nextPatient = current.patient;
            if (nextPatient.time(nextPatient.getTime()) < currentMin.time(currentMin.getTime())){
                min = current;
                minPre = previous;
            }
            previous = current;
            current = current.next;
        }
        // delete min
        if (minPre != null) {
            minPre.next = min.next;
        } else {
            head = min.next;
        }
        size--;
        return min.patient;
    }
}

public class Hospital3 extends HospitalBase {
    PatientLinkedList patients;
    public Hospital3() {
        /* Add your code here! */
        this.patients = new PatientLinkedList();
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        int hour = Integer.parseInt(patient.getTime().split(":", 2)[0]);
        if (hour >= 18 || hour < 8 || hour == 12) {
            return false;
        } else {
            this.patients.insertNode((Patient) patient);
            return true;
        }
    }

    @Override
    public Iterator<PatientBase> iterator() {
        /* Add your code here! */
        return new Iterator<PatientBase>() {

            @Override
            public boolean hasNext() {
                return patients.size > 0;
            }

            @Override
            public PatientBase next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return patients.findMin();
            }
        };
    }
    /* Add any extra functions below */
}


