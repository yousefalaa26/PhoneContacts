public class PhoneContacts {
    private final int capacity;
    private int size;
    private final HashNode[] Contacts;

    PhoneContacts(int capacity){
        this.capacity=capacity;
        this.Contacts = new HashNode[capacity];
        this.size=0;
    }

    private static class HashNode{
        Node node;
        boolean occupiedBefore;

        HashNode(String name, String phone){
            this.node = new Node(name, phone);
            this.occupiedBefore= true;
        }

        private static class Node{
            String name;
            String phone;

            Node(String name, String phone){
                this.name= name;
                this.phone= phone;
            }
        }
    }

    private boolean isFull(){
        return size == capacity;
    }
    private boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }


    private long hash(String key) {
        int i, l = key.length();
        long hash = 0;
        for (i = 0; i < l; i++) {
            hash += Character.getNumericValue(key.charAt(i));
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);

        if (hash > 0) return hash % capacity;
        else return -hash % capacity;
    }

    public void insert(String name, String phone){
        if (isFull()){
            System.out.println("Cannot insert "+name+", contacts are full");
            return;
        }
        int index = (int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            index=(index +1) % capacity;
        }
        Contacts[index]= new HashNode(name, phone);
        size++;
        System.out.println("Contact inserted successfully");
    }


    void search(String name){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        int index=(int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                System.out.println("found at index: "+index);
                System.out.println("Phone: " + Contacts[index].node.phone);
                return;
            }
            index=(index +1) % capacity;
        }
        System.out.println("Contact not found");
    }

    public void remove(String name){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        int index=(int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                Contacts[index].node = null;
                size--;
                System.out.println("Contact removed");
                return;
            }
            index=(index +1) % capacity;
        }
        System.out.println("Contact not found");
    }
    public void update(String name, String newPhone){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        int index=(int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                String oldPhone = Contacts[index].node.phone;
                Contacts[index].node.phone = newPhone;
                System.out.println("Contact updated from "+oldPhone+" to "+newPhone);
                return;
            }
            index=(index +1) % capacity;
        }
        System.out.println("Contact not found");
    }

    void display(){
        if(isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        for(int i=0; i<capacity; i++){
            try {
                System.out.println("Name: "+Contacts[i].node.name+", Phone: "+Contacts[i].node.phone);
                System.out.println("At index: "+i);
                System.out.println("----------------------------------");
            } catch (NullPointerException e) {
            }
        }
    }
}
