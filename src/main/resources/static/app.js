const $ = (sel) => document.querySelector(sel);
let editingUserId = null;
let editingTrilhaId = null;

// Competências seed map
const COMP_MAP = {
  1:'Inteligência Artificial',2:'Análise de Dados',3:'Segurança Cibernética',4:'Computação em Nuvem',
  5:'Automação e RPA',6:'Design de Experiência',7:'Comunicação',8:'Pensamento Crítico',
  9:'Empatia',10:'Green Tech',11:'Gestão de Projetos',12:'Liderança Colaborativa'
};

function showError(err){
  try {
    const msg = typeof err === 'string' ? err : (err?.message || JSON.stringify(err));
    alert('Erro: ' + msg);
  } catch { alert('Erro inesperado'); }
}

async function api(path, options={}){
  const res = await fetch(path, { headers: { 'Content-Type': 'application/json' }, ...options });
  const text = await res.text();
  const data = (()=>{ try { return JSON.parse(text); } catch { return text; } })();
  if(!res.ok) throw new Error(typeof data === 'string' ? data : JSON.stringify(data));
  return data;
}

async function loadStatus(){
  try {
    const [usuarios, trilhas] = await Promise.all([ api('/api/usuarios'), api('/api/trilhas') ]);
    $('#apiStatus').textContent = 'API online ✅';
    $('#seedUsuarios').textContent = usuarios.length;
    $('#seedTrilhas').textContent = trilhas.length;
    $('#seedCompetencias').textContent = '12 (seed)';
    $('#seedMatriculas').textContent = '6 (seed)';
  } catch {
    $('#apiStatus').textContent = 'API offline ❌';
  }
}

function compNames(ids){
  if(!Array.isArray(ids) || ids.length === 0) return '-';
  return ids.map(id => COMP_MAP[id] ? `${id}·${COMP_MAP[id]}` : String(id)).join(', ');
}

function renderUsuarios(list=[]){
  const tbody = $('#usuariosTable'); if(!tbody) return;
  tbody.innerHTML = list.map(u => `
    <tr class="border-b border-slate-800 last:border-0">
      <td class="py-2 pr-4">${u.id}</td>
      <td class="py-2 pr-4">${u.nome}</td>
      <td class="py-2 pr-4">${u.email}</td>
      <td class="py-2 pr-4">${u.areaAtuacao ?? '-'}</td>
      <td class="py-2 pr-4">${u.nivelCarreira ?? '-'}</td>
      <td class="py-2 pr-4 flex gap-2">
        <button data-id="${u.id}" class="editUser text-indigo-400">Editar</button>
        <button data-id="${u.id}" class="delUser text-red-400">Excluir</button>
      </td>
    </tr>`).join('');

  tbody.querySelectorAll('.delUser').forEach(btn => btn.addEventListener('click', async ()=>{
    try{
      if(confirm('Excluir este usuário?')){
        await fetch('/api/usuarios/' + btn.dataset.id, { method: 'DELETE' });
        await reloadAll();
      }
    }catch(err){ showError(err); }
  }));

  tbody.querySelectorAll('.editUser').forEach(btn => btn.addEventListener('click', async ()=>{
    try{
      const id = Number(btn.dataset.id);
      const user = await api('/api/usuarios/' + id);
      editingUserId = id;
      document.querySelector('#userModal h3').textContent = 'Editar usuário';
      $('#uNome').value = user.nome || '';
      $('#uEmail').value = user.email || '';
      $('#uArea').value = user.areaAtuacao || '';
      $('#uNivel').value = user.nivelCarreira || 'PLENO';
      $('#userModal').showModal();
    }catch(err){ showError(err); }
  }));
}

function renderTrilhas(list=[]){
  const grid = $('#trilhasGrid'); if(!grid) return;
  grid.innerHTML = list.map(t => `
    <article class="border border-slate-800 rounded-2xl p-4 shadow-sm">
      <div class="flex items-center justify-between">
        <div class="flex items-center gap-2">
          <span class="text-[11px] px-2 py-1 rounded border border-slate-700 text-slate-300">ID #${t.id}</span>
          <h3 class="font-semibold">${t.nome}</h3>
        </div>
        <span class="text-xs px-2 py-1 rounded bg-slate-800">${t.nivel}</span>
      </div>
      <p class="mt-2 text-sm text-slate-300">${t.descricao ?? ''}</p>
      <div class="mt-3 text-sm"><span class="font-semibold">Carga horária:</span> ${t.cargaHoraria}h</div>
      <div class="mt-1 text-xs text-slate-400">Foco: ${t.focoPrincipal ?? '-'}</div>
      <div class="mt-3 text-xs text-slate-400">Competências: ${compNames(t.competenciasIds)}</div>
    </article>`).join('');
}

function scoreBadge(score){
  const pct = Math.min(100, Math.max(0, score));
  return `<div class="mt-2 w-full h-2 bg-slate-800 rounded overflow-hidden">
    <div class="h-2 bg-indigo-600" style="width:${pct}%"></div>
  </div>`;
}

async function setupRecommendations(){
  try{
    const users = await api('/api/usuarios');
    const sel = $('#recUsuario'); if(!sel) return;
    sel.innerHTML = users.map(u => `<option value="${u.id}">${u.id} • ${u.nome}</option>`).join('');
    $('#doRecommend')?.addEventListener('click', async ()=>{
      try{
        const id = Number(sel.value);
        const recs = await api('/api/recomendacoes/usuario/' + id);
        const grid = $('#recGrid');
        grid.innerHTML = recs.map(r => `
          <article class="border border-slate-800 rounded-2xl p-4 shadow-sm">
            <div class="flex items-center justify-between">
              <h3 class="font-semibold">${r.trilhaNome}</h3>
              <span class="text-xs px-2 py-1 rounded bg-slate-800">${r.nivel}</span>
            </div>
            <div class="text-sm mt-1 text-slate-300">Score: <b>${r.score}</b>/100</div>
            ${scoreBadge(r.score)}
            <ul class="mt-2 text-xs text-slate-400 list-disc pl-5">
              ${(r.motivos||[]).map(m=>`<li>${m}</li>`).join('')}
            </ul>
            <button data-trilha="${r.trilhaId}" data-usuario="${id}" class="mt-3 px-3 py-2 rounded bg-indigo-600 text-white recMatricular">Matricular</button>
          </article>`).join('');
        grid.querySelectorAll('.recMatricular').forEach(btn => btn.addEventListener('click', async ()=>{
          try{
            await api('/api/matriculas', { method:'POST', body: JSON.stringify({ usuarioId: Number(btn.dataset.usuario), trilhaId: Number(btn.dataset.trilha) }) });
            alert('Matrícula criada!');
          } catch(err){ showError(err); }
        }));
      }catch(err){ showError(err); }
    });
  }catch{/* ignore */}
}

function setupModals(){
  const userModal = $('#userModal');
  const enrollModal = $('#enrollModal');
  const trilhaModal = $('#trilhaModal');

  $('#openUserModal')?.addEventListener('click', ()=>{
    editingUserId = null;
    document.querySelector('#userModal h3').textContent = 'Novo usuário';
    userModal.showModal();
  });

  $('#openEnrollModal')?.addEventListener('click', ()=> enrollModal.showModal());
  $('#openTrilhaModal')?.addEventListener('click', ()=>{
    editingTrilhaId = null;
    document.querySelector('#trilhaModal h3').textContent = 'Nova trilha';
    trilhaModal.showModal();
  });

  // CANCELAR
  $('#cancelUser')?.addEventListener('click', ()=> userModal.close());
  $('#cancelEnroll')?.addEventListener('click', ()=> enrollModal.close());
  $('#cancelTrilha')?.addEventListener('click', ()=> trilhaModal.close());

  $('#saveUser')?.addEventListener('click', async (e)=>{
    e.preventDefault();
    const payload = {
      nome: $('#uNome').value.trim(),
      email: $('#uEmail').value.trim(),
      areaAtuacao: $('#uArea').value.trim(),
      nivelCarreira: $('#uNivel').value
    };
    try{
      if(editingUserId){
        await api('/api/usuarios/' + editingUserId, { method:'PUT', body: JSON.stringify(payload) });
      } else {
        await api('/api/usuarios', { method:'POST', body: JSON.stringify(payload) });
      }
      editingUserId = null;
      userModal.close(); await reloadAll();
    }catch(err){ showError(err); }
  });

  $('#doEnroll')?.addEventListener('click', async (e)=>{
    e.preventDefault();
    const payload = { usuarioId: Number($('#mUsuarioId').value), trilhaId: Number($('#mTrilhaId').value) };
    try{
      await api('/api/matriculas', { method:'POST', body: JSON.stringify(payload) });
      enrollModal.close(); alert('Matrícula criada! 🎉');
    }catch(err){ showError(err); }
  });

  $('#saveTrilha')?.addEventListener('click', async ()=>{
    const payload = {
      nome: $('#tNome').value.trim(),
      descricao: $('#tDescricao').value.trim(),
      nivel: $('#tNivel').value,
      cargaHoraria: Number($('#tCarga').value),
      focoPrincipal: $('#tFoco').value.trim(),
      competenciasIds: $('#tCompetencias').value.split(',').map(s=>Number(s.trim())).filter(Boolean)
    };
    try{
      if(editingTrilhaId){
        await api('/api/trilhas/' + editingTrilhaId, { method:'PUT', body: JSON.stringify(payload) });
      } else {
        await api('/api/trilhas', { method:'POST', body: JSON.stringify(payload) });
      }
      $('#trilhaModal').close(); await reloadAll();
    }catch(err){ showError(err); }
  });
}

async function reloadAll(){
  const [usuarios, trilhas] = await Promise.all([ api('/api/usuarios'), api('/api/trilhas') ]);
  renderUsuarios(usuarios);
  renderTrilhas(trilhas);
  await loadStatus();
}

window.addEventListener('DOMContentLoaded', async ()=>{
  try{
    setupModals();
    await setupRecommendations();
    $('#btnReload')?.addEventListener('click', reloadAll);
    await reloadAll();
  }catch(err){ showError(err); }
});
