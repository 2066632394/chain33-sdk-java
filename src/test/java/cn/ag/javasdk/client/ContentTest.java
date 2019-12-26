package cn.ag.javasdk.client;

import cn.chain33.javasdk.client.RpcClient;
import cn.chain33.javasdk.model.rpcresult.QueryTransactionResult;
import cn.chain33.javasdk.utils.TransactionUtil;
import org.bitcoinj.wallet.DeterministicSeed;

public class ContentTest {

	    // ������������jsonRPC�ӿ�
		RpcClient client = new RpcClient("172.16.103.14",8801);
		
		// �û�˽Կ
		private static String producerSecret = "3990969DF92A5914F7B71EEB9A4E58D6E255F32BF042FEA5318FC8B3D50EE6E8";
		

		public static void main(String []args) throws Exception {
			
			
			
//			ContentTest test = new ContentTest();
//			// ҩƷ������������������
//			String content = "{\"ҩƷ���\":\"MEDICAL000001\",\"ҩƷͼƬHASH\":\"933a925767fe0ae387947f41690fc054\",\"ҩƷ����\":\"�Ϻ�\",\"������\":\"2019-12-12\",\"������\":\"�Ϻ�xxxxxx���޹�˾\"}";
//			String txHash = test.sendtx(content, producerSecret);
//			
//			System.out.println("����hash��" + txHash);
//			
//			// ���ݽ���hash��ѯ����
//			test.querytx(txHash);
						
		}
		
		// ���ͽ���
		public String sendtx(String content, String Secret) throws Exception {
			// user.write�����Լ����
			String contractName = "user.write";
			String creareTx = TransactionUtil.createTx(Secret, contractName, content, 10000000);
			String txHash = client.submitTransaction(creareTx);
			
			return txHash;
		}
		
		
		
		public void querytx(String txHash) throws InterruptedException {
			
			QueryTransactionResult queryTransaction1 = new QueryTransactionResult();
			// ���������ܻ���һ����ʱ����ߵȴ�һ��
			for (int i = 0; i < 20; i++) {
				queryTransaction1 = client.queryTransaction(txHash);
				if (null == queryTransaction1) {
					Thread.sleep(2000);
				} else {
					break;
				}
			}
			
			// �������߶�
			// TODO:�����Ҫ�������쳣���Է�queryTransaction1�ǿյ����쳣
//			System.out.println("����߶ȣ�" + queryTransaction1.getHeight());
//			// �������ʱ��
//			System.out.println("����ʱ�䣺" + queryTransaction1.getBlocktime());
//			// ���from��ַ
//			System.out.println("from��ַ��" + queryTransaction1.getFromaddr());
//			// ���to��ַ
//			System.out.println("to��ַ��" + queryTransaction1.getTx().getTo());
//			// �������hash
//			System.out.println("����hash:" + client.getBlockHash(queryTransaction1.getHeight()));

			// ��ý�����	
			String payload = queryTransaction1.getTx().getRawpayload();
			String s = payload.substring(2, payload.length());
			byte[] b = new byte[s.length() / 2];
			for (int i = 0; i < b .length; i++) {
				try {
					b [i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				s = new String(b, "GB2312");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("������Ϣ��" + s);
			
		}
}
