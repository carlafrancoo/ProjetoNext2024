package br.edu.cesarschool.next.poo.projetoreferencia.conta;


	public class ContaPoupanca extends ContaCorrente {
		private double percentualBonus;

		public ContaPoupanca() {
			super();
		}

		public ContaPoupanca(int agencia, String numero, double saldo, 
				String nomeCorrentista, double percentualBonus) {
			super(agencia, numero, saldo, nomeCorrentista);
			this.percentualBonus = percentualBonus;		
		}
		public double getPercentualBonus() {
			return percentualBonus;
		}
		public void setPercentualBonus(double percentualBonus) {
			this.percentualBonus = percentualBonus;
		}
		@Override
		void creditar(double valor) {
			super.creditar(valor *(1 + percentualBonus / 100));		 
		}
		public double obterAliquotaCpmf() {
			return 0.0;
		}

}
