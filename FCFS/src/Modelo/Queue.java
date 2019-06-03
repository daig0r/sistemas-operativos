package Modelo;

public class Queue<T> {

	private Node<T> head, tail;
	private int serialId;

	public Queue() {
		head = tail = null;
		serialId = 0;
	}

	public void add(T data) {

		Node<T> node = new Node<T>();
		node.setData(data);
		node.setNext(null);

		if (head == null) {
			head = node;
		} else
			tail.setNext(node);

		tail = node;
		serialId++;
	}

	public void append(T data) {
		add(data);
		serialId--;
	}

	public T poll() {

		T data;
		Node<T> aux = head;

		head = aux.getNext();
		data = aux.getData();
		aux = null;

		return data;
	}

	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}

	public int getSerialId() {
		return serialId;
	}

	@Override
	protected void finalize() throws Throwable {

		Node<T> aux;

		while (head != null) {
			aux = head;
			head = aux.getNext();
			aux = null;
		}

		head = null;
		System.gc();
	}
}
