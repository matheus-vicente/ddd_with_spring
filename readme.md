# Javagas Estacionamento

Aplicação de TDD e DDD em uma api de controle de vagas em um estacionamento, com Spring.

## Todo

- [x] Vagas devem conter: id, código, tipo e disponibilidade.
- [ ] Ticket deve conter: id, código, status, placa, tarifa, valor e data de criação.
  - [ ] O Status deve ser: pendente, pago ou cancelado.
- [ ] As formas de tarifa devem ser as seguintes:
  - [ ] Primeira hora + hora adicional
  - [ ] Diária
  - [ ] Mensal
  - [ ] Permanencia mínima
- [ ] Usuário pode criar vagas.
  - [ ] Uma vaga não pode ter código repetido.
- [ ] Usuário pode editar vagas.
- [ ] Usuário pode deletar vagas.
- [ ] Deve ser possível listar as vagas.
- [ ] Usuáio pode criar tickets.
  - [ ] Um Ticket nunca deve ter o mesmo código.
- [ ] O valor do Ticket só pode ser 0 caso esteja dentro da permanência mínima (15 minutos).
