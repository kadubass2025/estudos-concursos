# 📚 Sistema de Controle de Estudos para Concursos Públicos

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-green?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue?style=flat-square&logo=postgresql)
![Railway](https://img.shields.io/badge/Deploy-Railway-purple?style=flat-square)
![Status](https://img.shields.io/badge/Status-Online-brightgreen?style=flat-square)

> Sistema web completo para gestão e controle de estudos para concursos públicos,
> inspirado na plataforma Estudei. Desenvolvido com Java 21 e Spring Boot.

## 🌐 Acesse o Sistema
**[estudos-concursos-production.up.railway.app](https://estudos-concursos-production.up.railway.app)**

---

## 📋 Funcionalidades

- **📈 Dashboard completo** com KPIs, gráficos e visão geral em tempo real
- **🔥 Constância nos estudos** com calendário de dias estudados
- **📊 Painel de disciplinas** com tempo, acertos e erros por matéria
- **📈 Gráfico semanal** de horas e questões por dia
- **🎯 Metas semanais** de horas e questões com barra de progresso
- **📚 Planejamento de matérias** com prioridade e progresso automático
- **📝 Registro diário** de estudos com percentual de acerto automático
- **🔁 Revisão espaçada** (7, 15 e 30 dias — método Ebbinghaus)
- **🔄 Ciclo de estudos** com gráfico circular de distribuição de tempo
- **⏱ Cronômetro integrado** que salva automaticamente no registro diário
- **📋 Banco de assuntos** por matéria para marcar progresso

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 21 LTS | Linguagem principal |
| Spring Boot | 4.0.3 | Framework backend |
| Spring Data JPA | - | Persistência de dados |
| Hibernate | 7.2 | ORM |
| PostgreSQL | 18 | Banco de dados |
| Thymeleaf | 3.1 | Template engine |
| Bootstrap | 5.3 | Framework CSS |
| Chart.js | - | Gráficos interativos |
| Neon | - | Banco em nuvem |
| Railway | - | Deploy e hospedagem |
| Maven | 3.x | Gerenciador de dependências |

---

## 🏗️ Arquitetura
```
src/main/java/com/ricardosantos/estudos_concursos/
│
├── controller/          # Camada de apresentação (MVC)
│   ├── DashboardController.java
│   ├── MateriaController.java
│   ├── RegistroController.java
│   ├── RevisaoController.java
│   ├── CicloController.java
│   └── ApiController.java
│
├── service/             # Regras de negócio
│   ├── DashboardService.java
│   ├── MateriaService.java
│   ├── RegistroService.java
│   ├── RevisaoService.java
│   └── CicloService.java
│
├── repository/          # Acesso ao banco de dados
│   ├── MateriaRepository.java
│   ├── AssuntoRepository.java
│   ├── RegistroDiarioRepository.java
│   ├── RevisaoRepository.java
│   └── CicloItemRepository.java
│
└── model/               # Entidades JPA
    ├── Materia.java
    ├── Assunto.java
    ├── RegistroDiario.java
    ├── Revisao.java
    ├── CicloItem.java
    └── enums/
        ├── Prioridade.java
        ├── TipoEstudo.java
        ├── StatusRevisao.java
        └── NivelDificuldade.java
```

---

## 🚀 Como Rodar Localmente

### Pré-requisitos
- Java 21+
- PostgreSQL instalado
- Maven (ou usar o wrapper incluso)

### Passo a passo
```bash
# 1. Clone o repositório
git clone https://github.com/kadubass2025/estudos-concursos.git
cd estudos-concursos

# 2. Configure o banco de dados
# Crie um banco PostgreSQL chamado: estudos_concursos

# 3. Configure o application.properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/estudos_concursos
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# 4. Execute o projeto
./mvnw spring-boot:run

# 5. Acesse no navegador
# http://localhost:8080
```

---

## 📸 Screenshots

### Dashboard Principal
> KPIs, constância nos estudos, painel de disciplinas e gráfico semanal

### Registro Diário
> Formulário completo com cálculo automático de percentual de acertos

### Ciclo de Estudos
> Gráfico circular com distribuição de tempo por matéria

### Cronômetro Integrado
> Widget flutuante disponível em todas as telas

---

## 📁 Banco de Dados

O sistema utiliza **PostgreSQL** com as seguintes tabelas:
```sql
materia          -- Matérias do edital
assunto          -- Assuntos por matéria
registro_diario  -- Registro de cada sessão de estudo
revisao          -- Controle de revisões espaçadas
ciclo_item       -- Itens do ciclo de estudos
```

---

## 👨‍💻 Autor

**Ricardo Santos**

[![GitHub](https://img.shields.io/badge/GitHub-kadubass2025-black?style=flat-square&logo=github)](https://github.com/kadubass2025)

---

## 📄 Licença

Este projeto está sob a licença MIT.