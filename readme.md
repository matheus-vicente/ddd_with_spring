# Javagas

Javagas é uma api REST de controle de um estacionamento, construído utilizando os conceitos de DDD (Domain-Driven Design).

## Todo

- [x] Vagas devem conter: id, código, tipo e disponibilidade.
- [ ] Ticket deve conter: id, código, status, placa, tarifa, valor e data de criação.
  - [ ] O Status deve ser: pendente, pago ou cancelado.
- [x] Usuário pode criar vagas.
  - [x] Uma vaga não pode ter código repetido.
- [x] Usuário pode editar vagas.
  - [x] Editar código e tipo.
- [x] Usuário pode deletar vagas.
  - [x] Não deve ser possível deletar uma vaga ocupada.
- [x] Deve ser possível listar as vagas.
- [ ] Usuário pode criar tickets.
- [ ] Deve ser gerado um código aleatório e único para um Ticket.
- [ ] Um Ticket nunca deve ter código repetido.
- [ ] As formas de tarifa devem ser as seguintes:
  - [ ] Primeira hora + hora adicional
  - [ ] Diária
  - [ ] Mensal
  - [ ] Permanencia mínima
- [ ] O valor do Ticket só pode ser 0 caso esteja dentro da permanência mínima (15 minutos).
