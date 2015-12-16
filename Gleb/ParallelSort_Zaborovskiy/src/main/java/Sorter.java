
import java.util.*;

class Sorter extends Thread {//  ласс, сортирующий то, что считал Reader

    private Reader reader;// –идер, к которому прив€зан данный сортировщик
    public List<String> innerList;// ¬нутренний лист, куда копируютс€ слова из
    // листа ридера.
    private boolean PARAMETER_U;
    private boolean PARAMETER_I;
    private boolean isWorking;// работает или ожидает пр€мо сейчас
    private static int DELAY = 100;// ¬рем€ ожидани€ в случае нулевого размера
    // внутреннего списка работающего ридера.
    // [ms]
    private boolean hasEnded = false;// «авершил ли работу полностью. ѕосле

    // ожидани€ работа может продолжитьс€,
    // после того, как this.hasEnded=true -
    // нет.

    public boolean isWorking() {
	return isWorking;
    }

    public boolean hasEnded() {// 1 из 2-х!
	return hasEnded;
    }

    public Sorter(Reader read, String name, boolean param_u, boolean param_i) {
	reader = read;
	innerList = new ArrayList<String>();// ћожно было бы сет, в случае u
	setName(name);
	PARAMETER_U = param_u;
	PARAMETER_I = param_i;
    }

    /**
     * ‘ункци€ удал€ет из внутреннего списка сортировщика неуникальные строки.
     */
  //  public void deleteDoubledFromSublist() {
//	for (int i = 0; i < (innerList.size() - 1); i++) {
//	    if ((PARAMETER_I == true) && (innerList.get(i).compareToIgnoreCase(innerList.get(i + 1)) == 0)) {
//		innerList.remove(i + 1);
//		i -= 1;
//	    }
//	    if ((PARAMETER_I == false) && (innerList.get(i).compareTo(innerList.get(i + 1)) == 0)) {
//		innerList.remove(i + 1);
//		i -= 1;
//	    }
//	}
//    }

    @Override
    public void run() {
	String buff;
	// ѕроверка, не поздно ли был создан тред
	if (reader.hasNoMoreWords()) {
	    isWorking = false;
	    hasEnded = true;
	}
	// цикл, в котором сортировщик забирает слова из внутреннего списка ридера.
	while (true) {
	    if (reader.words.size() > 0) {
		try {
		    buff = reader.words.remove(0);
		    innerList.add(buff);
		    isWorking = true;
		} catch (IndexOutOfBoundsException e) {
		    isWorking = false;
		    if (reader.hasNoMoreWords()) {
			hasEnded = true;
			break;
		    }
		}
	    } else {
		if (!reader.hasNoMoreWords()) {
		    isWorking = false;
		    try {
			Thread.sleep(DELAY);
			// ≈сли ридер ещЄ работает, но слов в
			// его списке нет. ћожет
			// случитьс€, если будет создано
			// слишком большое количество сортировщиков.
			// ѕо-хорошему в таком случае лишние
			// сортировщики нужно удал€ть, но мне
			// пока что было лень продумывать логику.
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		} else {
		    SimpleComparator sc = new SimpleComparator(PARAMETER_I);
		    Collections.sort(innerList, sc);
		    if (PARAMETER_U == true) {
			sc.deleteDoubledFromList(innerList);
		    }
		    isWorking = false;
		    hasEnded = true;
		    break;
		}

	    }

	}
    }

}
