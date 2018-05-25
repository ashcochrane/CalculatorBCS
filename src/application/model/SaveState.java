package application.model;

import java.io.*;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Set;

public class SaveState {
	
	public SaveState() {
	}

    public void save(ArrayList<Job> jobs, Holidays ch) throws IOException, ClassNotFoundException  {

        ObjectOutputStream jobOutputStream =
                new ObjectOutputStream(new FileOutputStream("data/job.rtf"));
        ObjectOutputStream holidayOutputStream =
                new ObjectOutputStream(new FileOutputStream("data/holidays.rtf"));

        jobOutputStream.writeObject(jobs);
        jobOutputStream.close();
        for (Job j : jobs) {
        	System.out.println(j.getId());
        }
        System.out.println("Size: " + jobs.size());
        holidayOutputStream.writeObject(ch.getHolidays());
        holidayOutputStream.close();

    }

	@SuppressWarnings("unchecked")
	public ArrayList<Job> loadJobs() throws IOException, ClassNotFoundException  {
		ArrayList<Job> jobs = new ArrayList<>();
        ObjectInputStream jobInputStream =
                new ObjectInputStream(new FileInputStream("data/job.rtf"));

        jobs = (ArrayList<Job>) jobInputStream.readObject();
        for (Job j : jobs) {
        	System.out.println(j.getId());
        }
        System.out.println("Size: " + jobs.size());
        jobInputStream.close(); 
        return jobs;
    }
	
	@SuppressWarnings("unchecked")
	public Holidays loadHolidays() throws IOException, ClassNotFoundException  {
		Holidays ch = new Holidays();
        ObjectInputStream holidayInputStream =
                new ObjectInputStream(new FileInputStream("data/holidays.rtf"));
        
        ch.setHolidays((Set<MonthDay>) holidayInputStream.readObject());
        holidayInputStream.close();
        return ch;
    }
    
}