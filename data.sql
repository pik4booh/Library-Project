--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2024-06-16 22:51:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 139470)
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    id_book integer NOT NULL,
    author character varying(255),
    collection character varying(255),
    cote_number character varying(255) NOT NULL,
    release_date timestamp(6) without time zone NOT NULL,
    summary character varying(255),
    title character varying(255) NOT NULL
);


ALTER TABLE public.book OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 139477)
-- Name: book_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_category (
    id_book_category integer NOT NULL,
    book_id integer,
    category_id integer
);


ALTER TABLE public.book_category OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 139482)
-- Name: book_member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_member (
    id_book_member integer NOT NULL,
    on_the_spot integer NOT NULL,
    take_away integer NOT NULL,
    book_id integer,
    type_member_id integer
);


ALTER TABLE public.book_member OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 139487)
-- Name: book_theme; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_theme (
    id_book_theme integer NOT NULL,
    book_id integer,
    theme_id integer
);


ALTER TABLE public.book_theme OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 139492)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id_category integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 139497)
-- Name: copy_book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.copy_book (
    id_copy_book integer NOT NULL,
    cover character varying(255),
    edit_date timestamp(6) without time zone,
    isbn character varying(255) NOT NULL,
    language character varying(255),
    page_number integer,
    book_id integer
);


ALTER TABLE public.copy_book OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 147661)
-- Name: librarian; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.librarian (
    id_librarian integer NOT NULL,
    name character varying(255) NOT NULL,
    pwd character varying(255) NOT NULL,
    role_id integer
);


ALTER TABLE public.librarian OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 139504)
-- Name: loaning; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loaning (
    id_loaning integer NOT NULL,
    expected_return_date timestamp(6) without time zone NOT NULL,
    loaning_date timestamp(6) without time zone NOT NULL,
    copy_book_id integer,
    member_id integer,
    type_loaning_id integer,
    return_loaning_id integer
);


ALTER TABLE public.loaning OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 139509)
-- Name: member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member (
    id_member integer NOT NULL,
    address character varying(255),
    birth timestamp(6) without time zone NOT NULL,
    date_register timestamp(6) without time zone NOT NULL,
    identifiant character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    type_member_id integer
);


ALTER TABLE public.member OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 139536)
-- Name: primary_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.primary_sequence
    START WITH 10000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.primary_sequence OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 155872)
-- Name: return_loaning; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.return_loaning (
    id_return_loaning integer NOT NULL,
    return_date timestamp(6) without time zone NOT NULL,
    loaning_id integer
);


ALTER TABLE public.return_loaning OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 147668)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id_role integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 139516)
-- Name: sanction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sanction (
    id_sanction integer NOT NULL,
    date_begin timestamp(6) without time zone NOT NULL,
    date_end timestamp(6) without time zone NOT NULL,
    copy_book_id integer,
    member_id integer
);


ALTER TABLE public.sanction OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 139521)
-- Name: theme; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.theme (
    id_theme integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.theme OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 139526)
-- Name: type_loaning; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.type_loaning (
    id_type_loaning integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.type_loaning OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 139531)
-- Name: type_member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.type_member (
    id_type_member integer NOT NULL,
    coeff_sanction integer NOT NULL,
    name character varying(255) NOT NULL,
    nb_loaning_days integer NOT NULL
);


ALTER TABLE public.type_member OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 139470)
-- Dependencies: 215
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book (id_book, author, collection, cote_number, release_date, summary, title) FROM stdin;
10012	Albert Camus	Folio	FOL CAM	1942-01-01 00:00:00	L'Étranger est le récit de Meursault, un homme ordinaire vivant à Alger, dont la vie est bouleversée par un meurtre apparemment sans motif	L'Étranger
10013	Antoine de Saint-Exupéry	Folio Junior	FOL JSA	1943-01-01 00:00:00	Le Petit Prince est un conte philosophique qui raconte l'histoire d'un jeune prince venu d'une autre planète et de son voyage à travers les différentes planètes, où il rencontre des personnages symboliques et découvre des leçons de vie profondes.	Le Petit Prince
10014	Victor Hugo	Classiques Abreges	843 HUG	1862-01-01 00:00:00	Les Misérables est un roman monumental qui suit les vies entrelacées de plusieurs personnages, dont l'ancien forçat Jean Valjean, à travers les tumultes de la France du XIXe siècle.	Les Misérables
\.


--
-- TOC entry 4938 (class 0 OID 139477)
-- Dependencies: 216
-- Data for Name: book_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book_category (id_book_category, book_id, category_id) FROM stdin;
10042	10012	10000
10043	10012	10001
10044	10013	10001
\.


--
-- TOC entry 4939 (class 0 OID 139482)
-- Dependencies: 217
-- Data for Name: book_member; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book_member (id_book_member, on_the_spot, take_away, book_id, type_member_id) FROM stdin;
10021	0	1	10012	10003
10054	1	1	10013	10004
\.


--
-- TOC entry 4940 (class 0 OID 139487)
-- Dependencies: 218
-- Data for Name: book_theme; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book_theme (id_book_theme, book_id, theme_id) FROM stdin;
\.


--
-- TOC entry 4941 (class 0 OID 139492)
-- Dependencies: 219
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id_category, name) FROM stdin;
10000	Horror
10001	Love
\.


--
-- TOC entry 4942 (class 0 OID 139497)
-- Dependencies: 220
-- Data for Name: copy_book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.copy_book (id_copy_book, cover, edit_date, isbn, language, page_number, book_id) FROM stdin;
10015	Couverture souple	1972-01-01 00:00:00	978-2-07-040850-4	FR	186	10012
10016	Couverture rigide	1999-01-01 00:00:00	978-2-07-061236-7	FR	93	10013
10051	6544d3b6-61e2-4fc3-9b46-2bb9cbdfff1fCover1.jpg	1996-01-23 14:55:00	978-2-07-040850-4	FR	879	10012
\.


--
-- TOC entry 4950 (class 0 OID 147661)
-- Dependencies: 228
-- Data for Name: librarian; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.librarian (id_librarian, name, pwd, role_id) FROM stdin;
10002	Raitra	Raitra123	10000
\.


--
-- TOC entry 4943 (class 0 OID 139504)
-- Dependencies: 221
-- Data for Name: loaning; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loaning (id_loaning, expected_return_date, loaning_date, copy_book_id, member_id, type_loaning_id, return_loaning_id) FROM stdin;
10047	2024-05-20 17:10:00	2024-05-23 17:10:00	10015	10020	10019	\N
10052	2024-06-23 17:46:00	2024-06-16 17:46:00	10051	10020	10019	\N
10055	2024-06-23 18:58:00	2024-06-16 18:58:00	10051	10020	10019	\N
\.


--
-- TOC entry 4944 (class 0 OID 139509)
-- Dependencies: 222
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.member (id_member, address, birth, date_register, identifiant, name, type_member_id) FROM stdin;
10020	AB 158 BIS B	2003-01-01 00:00:00	2024-05-19 12:00:00	MBR2185	Princy	10003
10029	Itaosy Unis	2002-01-01 00:00:00	2024-05-19 12:00:00	MBR2186	Aro	10005
\.


--
-- TOC entry 4952 (class 0 OID 155872)
-- Dependencies: 230
-- Data for Name: return_loaning; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.return_loaning (id_return_loaning, return_date, loaning_id) FROM stdin;
10049	2024-05-23 17:12:28.695226	10047
10053	2024-06-16 17:49:18.785553	10052
10056	2024-06-16 18:59:49.704574	10055
\.


--
-- TOC entry 4951 (class 0 OID 147668)
-- Dependencies: 229
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id_role, name) FROM stdin;
10000	Administrator
\.


--
-- TOC entry 4945 (class 0 OID 139516)
-- Dependencies: 223
-- Data for Name: sanction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sanction (id_sanction, date_begin, date_end, copy_book_id, member_id) FROM stdin;
10048	2024-05-23 17:12:28.681229	2024-05-26 17:10:00	10015	10020
\.


--
-- TOC entry 4946 (class 0 OID 139521)
-- Dependencies: 224
-- Data for Name: theme; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.theme (id_theme, name) FROM stdin;
10007	Identité et Découverte de Soi
10008	Pouvoir et Corruption
10009	Amour et Perte
10010	Nature et Humanité
10011	Conflit et Résolution
\.


--
-- TOC entry 4947 (class 0 OID 139526)
-- Dependencies: 225
-- Data for Name: type_loaning; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.type_loaning (id_type_loaning, name) FROM stdin;
10019	Take Away
10018	On Spot
\.


--
-- TOC entry 4948 (class 0 OID 139531)
-- Dependencies: 226
-- Data for Name: type_member; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.type_member (id_type_member, coeff_sanction, name, nb_loaning_days) FROM stdin;
10003	2	Etudiant	7
10004	4	Professionnel	14
10005	8	Professeur	28
10006	6	Simple	7
\.


--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 227
-- Name: primary_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.primary_sequence', 10069, true);


--
-- TOC entry 4747 (class 2606 OID 139481)
-- Name: book_category book_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_category
    ADD CONSTRAINT book_category_pkey PRIMARY KEY (id_book_category);


--
-- TOC entry 4749 (class 2606 OID 139486)
-- Name: book_member book_member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_member
    ADD CONSTRAINT book_member_pkey PRIMARY KEY (id_book_member);


--
-- TOC entry 4745 (class 2606 OID 139476)
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id_book);


--
-- TOC entry 4751 (class 2606 OID 139491)
-- Name: book_theme book_theme_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_theme
    ADD CONSTRAINT book_theme_pkey PRIMARY KEY (id_book_theme);


--
-- TOC entry 4753 (class 2606 OID 139496)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id_category);


--
-- TOC entry 4755 (class 2606 OID 139503)
-- Name: copy_book copy_book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.copy_book
    ADD CONSTRAINT copy_book_pkey PRIMARY KEY (id_copy_book);


--
-- TOC entry 4771 (class 2606 OID 147667)
-- Name: librarian librarian_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.librarian
    ADD CONSTRAINT librarian_pkey PRIMARY KEY (id_librarian);


--
-- TOC entry 4757 (class 2606 OID 139508)
-- Name: loaning loaning_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT loaning_pkey PRIMARY KEY (id_loaning);


--
-- TOC entry 4761 (class 2606 OID 139515)
-- Name: member member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_pkey PRIMARY KEY (id_member);


--
-- TOC entry 4775 (class 2606 OID 155876)
-- Name: return_loaning return_loaning_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.return_loaning
    ADD CONSTRAINT return_loaning_pkey PRIMARY KEY (id_return_loaning);


--
-- TOC entry 4773 (class 2606 OID 147672)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id_role);


--
-- TOC entry 4763 (class 2606 OID 139520)
-- Name: sanction sanction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sanction
    ADD CONSTRAINT sanction_pkey PRIMARY KEY (id_sanction);


--
-- TOC entry 4765 (class 2606 OID 139525)
-- Name: theme theme_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.theme
    ADD CONSTRAINT theme_pkey PRIMARY KEY (id_theme);


--
-- TOC entry 4767 (class 2606 OID 139530)
-- Name: type_loaning type_loaning_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_loaning
    ADD CONSTRAINT type_loaning_pkey PRIMARY KEY (id_type_loaning);


--
-- TOC entry 4769 (class 2606 OID 139535)
-- Name: type_member type_member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_member
    ADD CONSTRAINT type_member_pkey PRIMARY KEY (id_type_member);


--
-- TOC entry 4759 (class 2606 OID 155866)
-- Name: loaning uk_awpk9ntkgd8rnktu7vmepwgkx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT uk_awpk9ntkgd8rnktu7vmepwgkx UNIQUE (return_loaning_id);


--
-- TOC entry 4777 (class 2606 OID 155878)
-- Name: return_loaning uk_epb7wibuti3l7mh7nmun83v8h; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.return_loaning
    ADD CONSTRAINT uk_epb7wibuti3l7mh7nmun83v8h UNIQUE (loaning_id);


--
-- TOC entry 4790 (class 2606 OID 139592)
-- Name: sanction fk2hs4rhy58l9ffgyqg1t2coilr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sanction
    ADD CONSTRAINT fk2hs4rhy58l9ffgyqg1t2coilr FOREIGN KEY (copy_book_id) REFERENCES public.copy_book(id_copy_book);


--
-- TOC entry 4784 (class 2606 OID 139567)
-- Name: copy_book fk56y8nx90qlwh01d2dobpulb0l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.copy_book
    ADD CONSTRAINT fk56y8nx90qlwh01d2dobpulb0l FOREIGN KEY (book_id) REFERENCES public.book(id_book);


--
-- TOC entry 4780 (class 2606 OID 139552)
-- Name: book_member fk5etasdbfq6jow2d9nhhbg7h0m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_member
    ADD CONSTRAINT fk5etasdbfq6jow2d9nhhbg7h0m FOREIGN KEY (type_member_id) REFERENCES public.type_member(id_type_member);


--
-- TOC entry 4782 (class 2606 OID 139562)
-- Name: book_theme fk80iclwij8wawuq47wiynuytse; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_theme
    ADD CONSTRAINT fk80iclwij8wawuq47wiynuytse FOREIGN KEY (theme_id) REFERENCES public.theme(id_theme);


--
-- TOC entry 4785 (class 2606 OID 139582)
-- Name: loaning fk8e1a0xixnn0lweclr8xov6jxu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT fk8e1a0xixnn0lweclr8xov6jxu FOREIGN KEY (type_loaning_id) REFERENCES public.type_loaning(id_type_loaning);


--
-- TOC entry 4781 (class 2606 OID 139547)
-- Name: book_member fk8wefomnlcah92bgo66bhuybmh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_member
    ADD CONSTRAINT fk8wefomnlcah92bgo66bhuybmh FOREIGN KEY (book_id) REFERENCES public.book(id_book);


--
-- TOC entry 4778 (class 2606 OID 139542)
-- Name: book_category fkam8llderp40mvbbwceqpu6l2s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_category
    ADD CONSTRAINT fkam8llderp40mvbbwceqpu6l2s FOREIGN KEY (category_id) REFERENCES public.category(id_category);


--
-- TOC entry 4786 (class 2606 OID 139572)
-- Name: loaning fkawnqrkb625c9f21jspos0jngv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT fkawnqrkb625c9f21jspos0jngv FOREIGN KEY (copy_book_id) REFERENCES public.copy_book(id_copy_book);


--
-- TOC entry 4793 (class 2606 OID 155884)
-- Name: return_loaning fkcx2p4g5nxpofvu4pyj9xcsfjg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.return_loaning
    ADD CONSTRAINT fkcx2p4g5nxpofvu4pyj9xcsfjg FOREIGN KEY (loaning_id) REFERENCES public.loaning(id_loaning);


--
-- TOC entry 4783 (class 2606 OID 139557)
-- Name: book_theme fke7ub65yb2t7pef1l3k5o2am0s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_theme
    ADD CONSTRAINT fke7ub65yb2t7pef1l3k5o2am0s FOREIGN KEY (book_id) REFERENCES public.book(id_book);


--
-- TOC entry 4787 (class 2606 OID 139577)
-- Name: loaning fkew7h69c896u6c3m0n34m3afh3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT fkew7h69c896u6c3m0n34m3afh3 FOREIGN KEY (member_id) REFERENCES public.member(id_member);


--
-- TOC entry 4792 (class 2606 OID 147673)
-- Name: librarian fkjvv5dyac2qap7er6llo2aau8w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.librarian
    ADD CONSTRAINT fkjvv5dyac2qap7er6llo2aau8w FOREIGN KEY (role_id) REFERENCES public.role(id_role);


--
-- TOC entry 4788 (class 2606 OID 155879)
-- Name: loaning fkkvahdptdwp4y5hgd8ql3k4r8o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaning
    ADD CONSTRAINT fkkvahdptdwp4y5hgd8ql3k4r8o FOREIGN KEY (return_loaning_id) REFERENCES public.return_loaning(id_return_loaning);


--
-- TOC entry 4791 (class 2606 OID 139597)
-- Name: sanction fkllorke79ox0j3lpk860t9x3e2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sanction
    ADD CONSTRAINT fkllorke79ox0j3lpk860t9x3e2 FOREIGN KEY (member_id) REFERENCES public.member(id_member);


--
-- TOC entry 4779 (class 2606 OID 139537)
-- Name: book_category fknyegcbpvce2mnmg26h0i856fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_category
    ADD CONSTRAINT fknyegcbpvce2mnmg26h0i856fd FOREIGN KEY (book_id) REFERENCES public.book(id_book);


--
-- TOC entry 4789 (class 2606 OID 139587)
-- Name: member fkok2bla8tfmuollrl1dci63ul8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT fkok2bla8tfmuollrl1dci63ul8 FOREIGN KEY (type_member_id) REFERENCES public.type_member(id_type_member);


-- Completed on 2024-06-16 22:51:32

--
-- PostgreSQL database dump complete
--

