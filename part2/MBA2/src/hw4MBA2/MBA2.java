package hw4MBA2;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

public class MBA2 {
	
//	TextArrayWritable() is from 
//	https://stackoverflow.com/questions/24274668/iterate-through-arraywritable-nosuchmethodexception
//	https://stackoverflow.com/questions/15810550/output-a-list-from-a-hadoop-map-reduce-job-using-custom-writable
	   public static class TextArrayWritable extends ArrayWritable {
	        public TextArrayWritable() {
	            super(Text.class);
	        }

	        public TextArrayWritable(String[] strings) {
	            super(Text.class);
	            Text[] texts = new Text[strings.length];
	            for (int i = 0; i < strings.length; i++) {
	                texts[i] = new Text(strings[i]);
	            }
	            set(texts);
	        }
	        
	        @Override
	        public String toString() {
	          return Arrays.toString(get());
	        }
	    }

 
	public static class TokenizerMapper extends Mapper<Object, Text, Text, ArrayWritable> {
 
		private Text data = new Text();
 
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			String[] products = value.toString().split("\\[|,|\\]|\\s+");
			List<String> list = new ArrayList<String>(Arrays.asList(products));
			list.removeAll(Arrays.asList("", null));
			// list = [P1, P2, 1]
			String key1 = list.get(0);
			List<String> temp1 = Arrays.asList(list.get(1), list.get(2));
			String key2 = list.get(1);
			List<String> temp2 = Arrays.asList(list.get(0), list.get(2));
			context.write(new Text(key1.toString()), new TextArrayWritable((String[]) temp1.toArray()));
			context.write(new Text(key2.toString()), new TextArrayWritable((String[]) temp2.toArray()));
 
		}
 
	}
 
	public static class IntSumReducer extends Reducer<Text, TextArrayWritable, Text, TextArrayWritable> {
 
		public void reduce(Text key, Iterable<TextArrayWritable> value, Context context)
				throws IOException, InterruptedException {
			
			List list = new ArrayList();
			for (TextArrayWritable values : value) {
				list.add(values.toString());
//				list.add(new TextArrayWritable (values));
			}
			String[] output = (String[]) list.toArray(new String[list.size()]);
			context.write(new Text(key), new TextArrayWritable(output));

		}
	}
 
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		final Log LOG = LogFactory.getLog(MBA2.class);
 
	    Configuration conf = new Configuration();
	    GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
	    String[] remainingArgs = optionParser.getRemainingArgs();
	    if ((remainingArgs.length != 2)) {
	      System.err.println("Usage: MBA2 <input> <output>");
	      System.exit(2);
	    }
	    List<String> otherArgs = new ArrayList<String>();
	    for (int i=0; i < remainingArgs.length; ++i) {
	      otherArgs.add(remainingArgs[i]);
	    }
	    Job job = Job.getInstance(conf, "MarketBasketAnalysis"); 
		job.setJarByClass(MBA2.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(TextArrayWritable.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TextArrayWritable.class);
		job.setNumReduceTasks(1);
	    FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

 
	}
}